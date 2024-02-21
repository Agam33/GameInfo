package com.org.gamecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.org.gamecatalog.R
import com.org.gamecatalog.data.model.Favorite
import com.org.gamecatalog.databinding.ItemFavoriteBinding

class FavoriteAdapter: ListAdapter<Favorite, FavoriteAdapter.MViewHolder>(listFavoriteComparator) {

  var onItemDeleteListener: OnItemDeleteListener? = null
  var onItemClickListener: OnItemClickListener? = null

  interface OnItemDeleteListener {
    fun onItemDelete(favorite: Favorite)
  }

  interface OnItemClickListener {
    fun onItemClick(favorite: Favorite)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
    MViewHolder(
      DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_favorite, parent, false)
    )

  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }

  inner class MViewHolder(
    val binding: ItemFavoriteBinding
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(favorite: Favorite) {
      binding.favorite = favorite
      binding.root.setOnClickListener { onItemClickListener?.onItemClick(favorite) }
      binding.ivFavorite.setOnClickListener { onItemDeleteListener?.onItemDelete(favorite) }
    }
  }

  companion object {
    private val listFavoriteComparator = object : DiffUtil.ItemCallback<Favorite>() {
      override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}