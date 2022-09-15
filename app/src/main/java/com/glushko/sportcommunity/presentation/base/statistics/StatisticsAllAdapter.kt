package com.glushko.sportcommunity.presentation.base.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glushko.sportcommunity.data.statistics.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.databinding.ItemStatisticsRowBinding
import com.glushko.sportcommunity.util.Constants

class StatisticsAllAdapter(private val fromOpen: Int): ListAdapter<PlayerStatisticDisplayData, StatisticsAllAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatisticsAllAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStatisticsRowBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: StatisticsAllAdapter.ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemStatisticsRowBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun onBind(item: PlayerStatisticDisplayData) = binding.run{
            textPlayerName.text = item.playerName
            textDopInfo.text = if (fromOpen == Constants.OPEN_FROM_TEAM) item.amplua else item.playerTeam
            textPoints.text = item.points.toString()
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<PlayerStatisticDisplayData>(){
        override fun areItemsTheSame(
            oldItem: PlayerStatisticDisplayData,
            newItem: PlayerStatisticDisplayData
        ): Boolean {
            return oldItem.playerId == newItem.playerId
        }

        override fun areContentsTheSame(
            oldItem: PlayerStatisticDisplayData,
            newItem: PlayerStatisticDisplayData
        ): Boolean {
            return oldItem == newItem
        }

    }
}