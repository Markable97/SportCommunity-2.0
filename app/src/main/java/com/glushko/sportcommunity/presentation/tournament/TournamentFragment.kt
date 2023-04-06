package com.glushko.sportcommunity.presentation.tournament

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.MenuHost
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.databinding.FragmentTournamentBinding
import com.glushko.sportcommunity.databinding.ItemTournamentTableRowBinding
import com.glushko.sportcommunity.presentation.base.BaseFragmentWithToolbarMenu
import com.glushko.sportcommunity.presentation.base.statistics.StatisticsTournamentAdapter
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.glushko.sportcommunity.presentation.main_screen.ui.MainViewModel
import com.glushko.sportcommunity.presentation.media.FullPhoto
import com.glushko.sportcommunity.presentation.tournament.model.AlreadyExistsFavoriteException
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.TournamentTableDisplayData
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.Resource
import com.glushko.sportcommunity.util.Result
import com.glushko.sportcommunity.util.extensions.addOnPageSelectedListener
import com.glushko.sportcommunity.util.extensions.getFullUrl
import com.glushko.sportcommunity.util.extensions.gone
import com.glushko.sportcommunity.util.extensions.showDebugUnderDevelopmentMessage
import com.glushko.sportcommunity.util.extensions.snackbar
import com.glushko.sportcommunity.util.extensions.toast
import com.glushko.sportcommunity.util.extensions.visible
import com.glushko.sportcommunity.util.succeeded
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TournamentFragment: BaseFragmentWithToolbarMenu<FragmentTournamentBinding>(R.layout.fragment_tournament) {

    private val viewModelMain: MainViewModel by activityViewModels()
    private val viewModel: TournamentViewModel by hiltNavGraphViewModels(R.id.nav_graph_tournament)

    override val menuRes: Int
        get() = R.menu.menu_tournament
    override val menuActions: Map<Int, (MenuItem) -> Boolean>
        get() = mapOf(
            R.id.menuWeb to {
                viewModelMain.liveDataMainScreen.value?.data?.tournamentUrl?.let { url ->
                    (requireActivity() as? MainActivity)?.openWeb(url.getFullUrl())
                }
                true
            },
            R.id.menuFavorite to {
                it.isChecked = !it.isChecked
                var isClick = false
                if (it.isChecked) {
                    when(val save = viewModelMain.saveFavorite()) {
                        is Result.Error -> {
                            if (save.exception is AlreadyExistsFavoriteException) {
                                isClick = false
                                AlertDialog.Builder(requireContext())
                                    .setTitle(R.string.favorite_warning_title)
                                    .setMessage(R.string.favorite_warning_message)
                                    .setPositiveButton(R.string.yes) { dialog, _ ->
                                        val change = viewModelMain.saveFavorite(true)
                                        if (change.succeeded) {
                                            snackbar(binding.root, getString(R.string.favorite_changed_success))
                                            checkFavorite(true)
                                        } else {
                                            snackbar(binding.root, getString(R.string.error_default))
                                        }
                                        dialog.dismiss()
                                    }
                                    .setNegativeButton(R.string.no) { dialog, _ ->
                                        checkFavorite(false)
                                        dialog.dismiss()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                        Result.Loading -> {}
                        is Result.Success -> {
                            snackbar(binding.root, getString(R.string.favorite_save_success))
                            isClick = true
                        }
                    }
                } else {
                    viewModelMain.deleteFavorite()
                    snackbar(binding.root, getString(R.string.favorite_delete_success))
                    isClick = true
                }
                isClick
            }
        )

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTournamentBinding = FragmentTournamentBinding.inflate(inflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? MenuHost)?.let { host ->
            setupMenu(host, viewLifecycleOwner)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
    }

    private fun setupObservers() {
        viewModelMain.run {
            liveDataMainScreen.observe(viewLifecycleOwner){
                showProgress(it is Resource.Loading)
                when(it){
                    is Resource.Empty -> {}
                    is Resource.Error -> {
                        toast(requireContext(), it.error!!.message ?: "")
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        viewModel.init()
                    }
                }
            }
            liveDataSelectedDivision.observe(viewLifecycleOwner) {
                Timber.d("$it")
                checkFavorite(it.selectedId == it.favoriteId)
            }
        }
        viewModel.run {
            liveDataTournamentInfo.observe(viewLifecycleOwner) {
                renderTournamentTable(it.tournamentTable, it.isCup)
            }
            liveDataStatistics.observe(viewLifecycleOwner){
                renderStatistics(it)
            }
        }
    }

    private fun openFullGrid(url: String) = binding.composeViewFullGrid.run {
        setContent {
            FullPhoto(
                openFullImage = mutableStateOf(true to url),
                onClickShare = { bitmap ->
                    viewModelMain.getUriToShare(bitmap, requireActivity().cacheDir.toString(), requireContext())
                }, onClickDownload = {
                    (requireActivity() as? MainActivity)?.downloadImage(url)
                }
            )
        }
    }

    private fun setupListener() = binding.run {
        itemTournamentTable.textTitle.setOnClickListener {
            val tournamentInfo = viewModel.liveDataTournamentInfo.value ?: return@setOnClickListener
            if (tournamentInfo.isCup) {
                openFullGrid(tournamentInfo.imageCupGrid?:"")
            } else {
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentTableFragment())
            }
        }
        itemStatistics.textTitle.setOnClickListener {
            viewModelMain.liveDataSelectedDivision.value?.selectedId?.let { divisionId ->
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToStatisticsFragment(
                    null,
                    Constants.OPEN_FROM_TOURNAMENT,
                    divisionId
                ))
            }
        }
        buttonMedia.setOnClickListener {
            findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTournamentMediaFragment())
        }
        buttonGames.setOnClickListener {
            findNavController().navigate(
                TournamentFragmentDirections
                    .actionTournamentFragmentToGamesFragment(
                        tournamentId = viewModelMain.liveDataSelectedDivision.value?.selectedId ?: 0
                    )
            )
        }
        buttonKdk.setOnClickListener {
            binding.root.showDebugUnderDevelopmentMessage()
        }
    }

    private fun renderTournamentTable(
        data: List<TournamentTableDisplayData>,
        cup: Boolean,
    ) = binding.itemTournamentTable.run {
        textTitle.text = getString(
            if (cup) R.string.tournament__cup_grid else R.string.tournament__tournament_table
        )
        if (cup) {
            groupTournamentItem.gone()
        } else {
            groupTournamentItem.visible()
            data.forEachIndexed { index, tournamentTableDisplayData ->
                when(index){
                    0 -> renderRow(tournamentTableDisplayData, 1, itemRowFirst)
                    1 -> renderRow(tournamentTableDisplayData, 2, itemRowSecond)
                    2 -> renderRow(tournamentTableDisplayData, 3, itemRowThird)
                    3 -> renderRow(tournamentTableDisplayData, 4, itemRowFourth)
                }
            }
        }
    }

    private fun renderRow(row: TournamentTableDisplayData, position: Int, bindingRow: ItemTournamentTableRowBinding){
        bindingRow.apply {
            root.setOnClickListener{
                findNavController().navigate(TournamentFragmentDirections.actionTournamentFragmentToTeamFragment(
                    row.teamName, row.teamId, row.teamImage
                ))
            }
            textPosition.text = position.toString()
            val image = row.teamImage ?: "${Constants.BASE_URL_IMAGE}${row.teamName}.png"
            imageTeam.load(image)
            textTeamName.text = row.teamName
            textGames.text = row.games.toString()
            textWins.text = row.wins.toString()
            textDraws.text = row.draws.toString()
            textLoses.text = row.losses.toString()
            textDifferences.text = row.scCon.toString()
            textPoints.text = row.points.toString()
        }
    }

    private fun renderStatistics(data: List<PlayerStatisticAdapter>) = binding.itemStatistics.run {
        viewPagerStatistics.adapter = StatisticsTournamentAdapter(
            fromOpen = Constants.OPEN_FROM_TOURNAMENT,
            onClickPlayer = { id, name ->
                findNavController().navigate(
                    TournamentFragmentDirections.actionTournamentFragmentToPlayerInfoFragment(id, name)
                )
            }
        ).apply { submitList(data) }
        viewPagerStatistics.addOnPageSelectedListener {  }
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { _, _ -> }.attach()
    }

}