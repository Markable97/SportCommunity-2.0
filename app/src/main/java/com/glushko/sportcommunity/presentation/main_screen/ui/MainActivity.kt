package com.glushko.sportcommunity.presentation.main_screen.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var toggle: ActionBarDrawerToggle

    private val destinationWithBack = listOf(R.id.detailMatchFragment)

    private var isClearBackStack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupDrawerToggle()


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
            Timber.d("Пришло в addOnDestinationChangedListener = ${destination.id} parent = ${destination.parent?.startDestinationId}")
            if(destination.id in destinationWithBack){
                showBackButton(true)
            }else{
                showBackButton(false)
            }
            if(isClearBackStack){
                isClearBackStack = !isClearBackStack
                destination.parent?.startDestinationId?.let {startDestination ->
                    if(startDestination != destination.id){
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.calendarFragment, false)
                            .build()

                        navController.navigate(startDestination, null, navOptions)
                    }
                }
            }
        }


        setupObservers()

        binding.navigationView.setNavigationItemSelectedListener {
            Timber.d("Клик по боковой менюшки id=${it.itemId}  title=${it.title} ")
            viewModel.chooseDivision(it.itemId)
            binding.toolbar.title = it.title
            binding.drawerLayout.close()
            isClearBackStack = true
            true
        }
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

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fm: FragmentManager = supportFragmentManager
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            } else {
                super.onBackPressed()
            }
            showBackButton(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupObservers() {
        viewModel.apply {
            liveDataLeagues.observe(this@MainActivity){
                initSpinnerLeagues(it)
            }
        }
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

    private fun addMenuItemInNavMenuDrawer(divisions: List<DivisionDisplayData>) {
        val menu = binding.navigationView.menu
        menu.clear()
        divisions.forEachIndexed { index, division ->
            menu.add(Menu.NONE/*groupId*/, division.id, index, division.name).isCheckable = true
        }
        binding.navigationView.invalidate()
        val firstItem = binding.navigationView.menu.getItem(0)
        firstItem.isChecked = true
        isClearBackStack = true
        viewModel.chooseDivision(firstItem.itemId)
        binding.toolbar.title = firstItem.title
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}