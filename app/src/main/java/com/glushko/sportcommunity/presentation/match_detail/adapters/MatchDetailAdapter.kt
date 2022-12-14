package com.glushko.sportcommunity.presentation.match_detail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.data.match_detail.model.MatchAction
import com.glushko.sportcommunity.data.match_detail.model.MatchSegment
import com.glushko.sportcommunity.data.match_detail.model.PlayerInMatchSegment
import com.glushko.sportcommunity.data.match_detail.model.getDrawable
import com.glushko.sportcommunity.databinding.ItemMatchActionGuestBinding
import com.glushko.sportcommunity.databinding.ItemMatchActionHomeBinding
import com.glushko.sportcommunity.databinding.ItemMatchSegmentBinding

class MatchDetailAdapter : ListAdapter<PlayerInMatchSegment, MatchDetailAdapter.BaseViewHolder>(ItemCallback){

    companion object{
        private const val TYPE_ACTION_HOME = 0
        private const val TYPE_ACTION_GUEST = 1
        private const val TYPE_SEGMENT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).segment){
            MatchSegment.ACTION_HOME -> TYPE_ACTION_HOME
            MatchSegment.ACTION_GUEST -> TYPE_ACTION_GUEST
            else -> TYPE_SEGMENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            TYPE_ACTION_HOME -> {
                ViewHolderHome(
                    ItemMatchActionHomeBinding.inflate(layoutInflater, parent, false)
                )
            }
            TYPE_ACTION_GUEST -> {
                ViewHolderGuest(
                    ItemMatchActionGuestBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> {
                ViewHolderSegment(
                    ItemMatchSegmentBinding.inflate(layoutInflater, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    abstract class BaseViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view){
        abstract fun bind(item: PlayerInMatchSegment)
    }

    inner class ViewHolderSegment(
        private val binding: ItemMatchSegmentBinding,
    ): BaseViewHolder(binding.root) {
        override fun bind(item: PlayerInMatchSegment) = binding.run {
            val textSegmentId = when(item.segment){
                MatchSegment.START -> R.string.match_detail_segment_start
                MatchSegment.END -> R.string.match_detail_segment_end
                MatchSegment.BREAK -> R.string.match_detail_segment_break
                else -> -1
            }
            textSegments.text = binding.root.context.getText(textSegmentId)
        }
    }

    inner class ViewHolderHome(
        private val binding: ItemMatchActionHomeBinding
    ): BaseViewHolder(binding.root){
        override fun bind(item: PlayerInMatchSegment): Unit = binding.run {
            textTimeAction.text = "${item.player?.timeAction}`"
            textPlayer.text = item.player?.playerName
            textAssistant.isVisible = item.player?.assist != null
            textAssistant.text = item.player?.assist
            val drawableAction = item.player?.typeAction?.getDrawable()
            drawableAction?.let {
                imageTypeAction.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        drawableAction
                    )
                )
            }
        }

    }

    inner class ViewHolderGuest(
        private val binding: ItemMatchActionGuestBinding
    ): BaseViewHolder(binding.root){
        override fun bind(item: PlayerInMatchSegment): Unit = binding.run{
            textTimeAction.text = "${item.player?.timeAction}`"
            textPlayer.text = item.player?.playerName
            textAssistant.isVisible = item.player?.assist != null
            textAssistant.text = item.player?.assist
            val drawableAction = item.player?.typeAction?.getDrawable()
            drawableAction?.let {
                imageTypeAction.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        drawableAction
                    )
                )
            }
        }

    }

    private object ItemCallback : DiffUtil.ItemCallback<PlayerInMatchSegment>() {
        override fun areItemsTheSame(
            oldItem: PlayerInMatchSegment,
            newItem: PlayerInMatchSegment
        ): Boolean {
            return oldItem.player?.idAction == newItem.player?.idAction
        }

        override fun areContentsTheSame(
            oldItem: PlayerInMatchSegment,
            newItem: PlayerInMatchSegment
        ): Boolean {
            return oldItem == newItem
        }

    }

}