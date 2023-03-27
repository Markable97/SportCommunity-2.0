package com.glushko.sportcommunity.presentation.main_screen.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.main_screen.division.model.DivisionDisplayData
import com.glushko.sportcommunity.data.main_screen.leagues.model.LeaguesDisplayData
import com.glushko.sportcommunity.databinding.ActivityMainBinding
import com.glushko.sportcommunity.databinding.HeaderNavigationDrawerBinding
import com.glushko.sportcommunity.presentation.main_screen.vm.MainViewModel
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.showGone
import com.glushko.sportcommunity.util.extensions.toast
import com.glushko.sportcommunity.util.extensions.toastLong
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MenuHost {

    companion object{
        private const val MENU_ITEM_SETTING = -1
        private const val MENU_ITEM_ABOUT = -2
        private const val MENU_ORDER_FOOTER = 50
    }

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val bindingHeader: HeaderNavigationDrawerBinding
        get() = HeaderNavigationDrawerBinding.bind(binding.navigationView.getHeaderView(0))

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var toggle: ActionBarDrawerToggle

    private val destinationWithoutLabel = listOf(R.id.createScheduleFragment, R.id.statisticsFragment,
        R.id.tournamentTableFragment, R.id.squadFragment, R.id.tournamentMediaFragment, R.id.galleryFragment,
        R.id.gamesFragment, R.id.teamMediaFragment, R.id.matchesPlayer
    )

    private val destinationWithoutBack = listOf(
        R.id.eventsFragment, R.id.calendarFragment, R.id.resultsFragment,
        R.id.tournamentFragment, R.id.adminFragment, R.id.aboutFragment,
        R.id.settingFragment
    )/*listOf(R.id.tournamentMediaFragment, R.id.detailMatchFragment, R.id.teamFragment,
        R.id.tournamentTableFragment, R.id.statisticsFragment,
        R.id.squadFragment, R.id.squadFragment, R.id.scheduleFragment, R.id.assignMatchesFragment,
        R.id.createScheduleFragment, R.id.editMatchListFragment, R.id.editMatchFragment, R.id.protocolFragment,
        R.id.galleryFragment, R.id.gamesFragment, R.id.playerInfoFragment
    )*/
    private val destinationWithBottomBar = listOf(
        R.id.eventsFragment, R.id.calendarFragment, R.id.resultsFragment, R.id.tournamentFragment, R.id.adminFragment
    )
    private val destinationDrawerMenu = listOf(R.id.aboutFragment, R.id.settingFragment)
    private var backupTitle: String = ""
    private var backupItem: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupDrawerToggle()
        setupObservers()
        //setupDrawerMenu()
        setupListener()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener {item ->
            val selectedMenuItemNavGraph = navController.graph.findNode(item.itemId) as? NavGraph?
            selectedMenuItemNavGraph?.let { menuGraph ->
                navController.popBackStack(menuGraph.startDestinationId, false)
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.tag("MarkDev").d("Пришло в addOnDestinationChangedListener = ${destination.id} parent = ${destination.parent?.startDestinationId}")
            if(destination.id !in destinationWithoutBack){
                showBackButton(true)
                if (destination.id !in destinationWithoutLabel){
                    binding.toolbar.title = destination.label
                }
            }else{
                showBackButton(false)
            }

            binding.bottomNav.isVisible = destination.id in destinationWithBottomBar
        }

        binding.navigationView.setNavigationItemSelectedListener {
            Timber.tag("MarkDev").d("Клик по боковой менюшки id=${it.itemId}  title=${it.title} ")
            if (it.itemId in destinationDrawerMenu){
                navController.navigate(it.itemId)
            } else {
                /*val navOptions = NavOptions.Builder()
                    .setPopUpTo(navController.graph.startDestinationId, true)
                    .build()*/

                //navController.navigate(navController.graph.startDestinationId, null, navOptions)
                viewModel.chooseDivision(it.itemId)
                backupTitle = it.title.toString()
                backupItem = it.itemId
            }
            setToolbarTitle(it.title.toString())
            binding.drawerLayout.close()
            true
        }
        getTokenFirebase()
    }

    private fun getTokenFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { v: Task<String> ->
            if (v.isSuccessful) {
                val firebaseToken = v.result
                Timber.d("Firebase token -> $firebaseToken")
            }
        }
    }

    /*private fun setupDrawerMenu() {
        binding.navigationView.menu.apply {
            add(Menu.CATEGORY_SYSTEM*//*groupId*//*, R.id.settingFragment, MENU_ORDER_FOOTER, getString(R.string.menu_setting)).isCheckable = true
            add(Menu.CATEGORY_SYSTEM*//*groupId*//*, R.id.aboutFragment, MENU_ORDER_FOOTER + 1, getString(R.string.menu_about)).isCheckable = true
        }
    }*/

    private fun setupListener() {
        bindingHeader.run {
            btnRetry.setOnClickListener {
                viewModel.getLeagues()
                it.isVisible = false
                textLeagueName.isVisible = true
                spinnerLeagues.isVisible = true
            }
        }
    }

    //call from Activity
    fun setToolbarTitle(title: String){
        binding.toolbar.title = title
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setupDrawerToggle() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.toolbarNavigationClickListener = View.OnClickListener {
            if (toggle.isDrawerIndicatorEnabled) {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            } else {
                onBackPressed()
            }
        }
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun showBackButton(isBack: Boolean) {
        toggle.isDrawerIndicatorEnabled = !isBack
        supportActionBar?.setDisplayHomeAsUpEnabled(isBack)
        toggle.syncState()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fm: FragmentManager = supportFragmentManager
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            } else {
                setToolbarTitle(backupTitle)
                super.onBackPressed()
            }
            backupItem?.let {
                binding.navigationView.setCheckedItem(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.apply {
            liveDataLeagues.observe(this@MainActivity){
                when(it){
                    is Resource.Error -> {
                        toast(this@MainActivity, it.error!!.message ?: "")
                        renderRetryHeader()
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        initSpinnerLeagues(it.data!!)
                    }
                    is Resource.Empty -> {}
                }
            }
           eventShareUri.observe(this@MainActivity){ resultUri ->
                when(resultUri){
                    is Result.Error -> {
                        toastLong(this@MainActivity, resultUri.exception.message ?: "Error share")
                    }
                    Result.Loading -> {
                    }
                    is Result.Success -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, resultUri.data)
                        }
                        startActivity(Intent.createChooser(intent, "Share photo"))
                    }
                }
            }
        }
    }

    private fun renderRetryHeader() = bindingHeader.run {
        textLeagueName.isVisible = false
        spinnerLeagues.isVisible = false
        btnRetry.isVisible = true
    }

    private fun initSpinnerLeagues(leagues: List<LeaguesDisplayData>) {
        HeaderNavigationDrawerBinding.bind(binding.navigationView.getHeaderView(0)).run {
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, leagues)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLeagues.adapter = adapter
            spinnerLeagues.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelect: View?,
                    selectItemPosition: Int,
                    selectedId: Long
                ) {
                    addMenuItemInNavMenuDrawer(leagues[selectItemPosition].divisions)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    fun showProgress(show: Boolean) {
        binding.progressBar.showGone(show)
    }

    fun downloadImage(url: String){
        try {
            val fileName = "${System.currentTimeMillis()}"
            val dm = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val downloadUri: Uri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(fileName)
                .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + fileName + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(this, R.string.download_start, Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Timber.e(e)
            Toast.makeText(this, R.string.download_error, Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun openWeb(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    private fun addMenuItemInNavMenuDrawer(divisions: List<DivisionDisplayData>) {
        val menu = binding.navigationView.menu
        menu.removeGroup(Menu.FIRST)
        divisions.forEachIndexed { index, division ->
            menu.add(Menu.FIRST/*groupId*/, division.id, index, division.name).isCheckable = true
        }
        binding.navigationView.invalidate()
        val firstItem = binding.navigationView.menu.getItem(0)
        firstItem.isChecked = true
        viewModel.chooseDivision(firstItem.itemId)
        backupTitle = firstItem.title.toString()
        backupItem = firstItem.itemId
        setToolbarTitle(backupTitle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}