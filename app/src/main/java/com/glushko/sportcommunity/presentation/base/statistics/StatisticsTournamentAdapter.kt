package com.glushko.sportcommunity.presentation.base.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticAdapter
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.presentation.tournament.model.title
import com.glushko.sportcommunity.databinding.ItemStatisticsBinding
import com.glushko.sportcommunity.databinding.ItemStatisticsRowBinding
import com.glushko.sportcommunity.util.Constants

class StatisticsTournamentAdapter(private val fromOpen: Int): ListAdapter<PlayerStatisticAdapter, StatisticsTournamentAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStatisticsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemStatisticsBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun onBind(item: PlayerStatisticAdapter){
            binding.textSubtitle.text = binding.root.context.getText(
                item.typeStatistics.title
            )
            item.firstPlayer?.let {
                renderRow(binding.itemFirst, it)
            }
            item.secondPlayer?.let {
                renderRow(binding.itemSecond, it)
            }
            item.thirdPlayer?.let {
                renderRow(binding.itemThird, it)
            }
        }

        private fun renderRow (bindingRow: ItemStatisticsRowBinding, player: PlayerStatisticDisplayData){
            bindingRow.apply {
                textPlayerName.text = player.playerName
                textDopInfo.text = if (fromOpen == Constants.OPEN_FROM_TEAM) player.amplua else player.playerTeam
                textPoints.text = player.points.toString()
                imagePlayerAvatar.load(player.urlAvatar){
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<PlayerStatisticAdapter>(){
        override fun areItemsTheSame(
            oldItem: PlayerStatisticAdapter,
            newItem: PlayerStatisticAdapter
        ): Boolean {
            return oldItem.typeStatistics == newItem.typeStatistics
        }

        override fun areContentsTheSame(
            oldItem: PlayerStatisticAdapter,
            newItem: PlayerStatisticAdapter
        ): Boolean {
            return oldItem == newItem
        }

    }

}