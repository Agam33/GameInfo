package com.org.gamecatalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.gamecatalog.data.model.Genre
import com.org.gamecatalog.databinding.ItemGenreBinding

class ItemGenreAdapter: RecyclerView.Adapter<ItemGenreAdapter.GenreViewHolder>() {

  private var isSelected = false

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
    TODO("Not yet implemented")
  }

  override fun getItemCount(): Int {
    TODO("Not yet implemented")
  }

  override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
    TODO("Not yet implemented")
  }

  inner class GenreViewHolder(
    private val binding: ItemGenreBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre) {

    }
  }
}