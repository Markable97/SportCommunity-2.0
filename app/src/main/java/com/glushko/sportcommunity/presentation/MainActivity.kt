package com.glushko.sportcommunity.presentation

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.ActivityMainBinding
import com.glushko.sportcommunity.databinding.HeaderNavigationDrawerBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!


    private val leagues = listOf(
        LeaguesDisplayData(
            "Восточная лига", 1, listOf(
                DivisionDisplayData("Дивизион 1", 1),
                DivisionDisplayData("Дивизион 2", 2),
                DivisionDisplayData("Дивизион 3", 3),
                DivisionDisplayData("Дивизион 4", 4),
            )
        ),
        LeaguesDisplayData(
            "Северная лига", 2, listOf(
                DivisionDisplayData("Дивизион 5", 5),
                DivisionDisplayData("Дивизион 6", 6),
                DivisionDisplayData("Дивизион 7", 7),
                DivisionDisplayData("Дивизион 8", 8),
            )
        ),
        LeaguesDisplayData(
            "Западная лига", 3, listOf(
                DivisionDisplayData("Дивизион 9", 9),
                DivisionDisplayData("Дивизион 10", 10),
                DivisionDisplayData("Дивизион 11", 11),
                DivisionDisplayData("Дивизион 12", 12),
            )
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        initSpinnerLeagues(leagues)

        binding.navigationView.setNavigationItemSelectedListener {
            Timber.d("Клик по боковой менюшки id=${it.itemId}  title=${it.title} ")
            binding.toolbar.title = it.title
            binding.drawerLayout.close()
            true
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

data class LeaguesDisplayData(
    val name: String,
    val id: Int,
    val divisions: List<DivisionDisplayData>
) {
    override fun toString(): String {
        return name
    }
}

data class DivisionDisplayData(val name: String, val id: Int)