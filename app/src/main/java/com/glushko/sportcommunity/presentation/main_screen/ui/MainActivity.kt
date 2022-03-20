package com.glushko.sportcommunity.presentation.main_screen.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)


        setupObservers()

        binding.navigationView.setNavigationItemSelectedListener {
            Timber.d("Клик по боковой менюшки id=${it.itemId}  title=${it.title} ")
            viewModel.chooseDivision(it.itemId)
            binding.toolbar.title = it.title
            binding.drawerLayout.close()
            true
        }
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}