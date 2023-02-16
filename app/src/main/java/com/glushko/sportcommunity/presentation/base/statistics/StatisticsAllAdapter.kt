package com.glushko.sportcommunity.presentation.base.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.glushko.sportcommunity.presentation.tournament.model.PlayerStatisticDisplayData
import com.glushko.sportcommunity.databinding.ItemStatisticsRowBinding
import com.glushko.sportcommunity.util.Constants
import com.glushko.sportcommunity.util.extensions.setSafeOnClickListener

class StatisticsAllAdapter(
    private val fromOpen: Int,
    private val onClickItem: (Int, String) -> Unit
    ): ListAdapter<PlayerStatisticDisplayData, StatisticsAllAdapter.ViewHolder>(DiffCallback) {

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

        private var selectItem: PlayerStatisticDisplayData? = null

        init {
            binding.root.setSafeOnClickListener {
                selectItem?.let { player ->
                    onClickItem.invoke(player.playerId, player.playerName)
                }
            }
        }

        fun onBind(item: PlayerStatisticDisplayData) = binding.run{
            selectItem = item
            textPlayerName.text = item.playerName
            textDopInfo.text = if (fromOpen == Constants.OPEN_FROM_TEAM) item.amplua else item.playerTeam
            textPoints.text = item.points.toString()
            imagePlayerAvatar.load(item.urlAvatar){
                transformations(CircleCropTransformation())
            }
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