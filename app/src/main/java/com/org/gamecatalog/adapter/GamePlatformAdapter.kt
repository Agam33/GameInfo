package com.org.gamecatalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.gamecatalog.data.model.GamePlatform
import com.org.gamecatalog.databinding.ItemCardPlatformBinding

class GamePlatformAdapter(
  private val list: List<GamePlatform>
): RecyclerView.Adapter<GamePlatformAdapter.GamePlatformViewHolder>() {

  inner class GamePlatformViewHolder(
      private val binding: ItemCardPlatformBinding
  ): RecyclerView.ViewHolder(binding.root) {
    fun bind(gamePlatform: GamePlatform) {
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamePlatformViewHolder =
    GamePlatformViewHolder(
      ItemCardPlatformBinding.inflate(LayoutInflater.from(parent.context))
    )

  override fun getItemCount(): Int = list.size

  override fun onBindViewHolder(holder: GamePlatformViewHolder, position: Int) {
    holder.bind(list[position])
  }
}