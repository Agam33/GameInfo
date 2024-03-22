package com.org.gamecatalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.org.gamecatalog.R
import com.org.gamecatalog.data.model.Game
import com.org.gamecatalog.databinding.ItemGameBinding

class GameAdapter: PagingDataAdapter<Game, GameAdapter.GameViewHolder>(gameComparator) {

  var onItemClickListener: OnItemClickListener? = null

  override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
    GameViewHolder(
      DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_game, parent, false)
    )

  inner class GameViewHolder(
    private val binding: ItemGameBinding
  ): RecyclerView.ViewHolder(binding.root) {
    fun bind(game: Game) {
      binding.game = game
      binding.root.setOnClickListener { onItemClickListener?.onItemClicked(game) }
    }
  }

  interface OnItemClickListener {
    fun onItemClicked(game: Game)
  }

  companion object {
    private val gameComparator = object : DiffUtil.ItemCallback<Game>() {
      override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
      }
    }
  }
}