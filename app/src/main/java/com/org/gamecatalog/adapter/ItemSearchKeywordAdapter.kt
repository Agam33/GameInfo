package com.org.gamecatalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.org.gamecatalog.R
import com.org.gamecatalog.data.model.SearchKeyword
import com.org.gamecatalog.databinding.ItemSearchKeywordBinding

class ItemSearchKeywordAdapter: ListAdapter<SearchKeyword, ItemSearchKeywordAdapter.MViewHolder>(
    searchKeywordComparator
) {

  var onKeywordClickListener: OnKeywordClickListener? = null
  var onDeleteClickListener: OnDeleteClickListener? = null

  interface OnKeywordClickListener {
    fun onItemKeyword(searchKeyword: SearchKeyword)
  }

  interface OnDeleteClickListener {
    fun onItemDelete(searchKeyword: SearchKeyword)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder =
    MViewHolder(
      DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search_keyword, parent, false)
    )
  override fun onBindViewHolder(holder: MViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }

  inner class MViewHolder(
    val binding: ItemSearchKeywordBinding
  ): RecyclerView.ViewHolder(binding.root) {
    fun bind(searchKeyword: SearchKeyword) {
      binding.text = searchKeyword.keyword
      binding.tvKeyword.setOnClickListener { onKeywordClickListener?.onItemKeyword(searchKeyword) }
      binding.ivDelete.setOnClickListener { onDeleteClickListener?.onItemDelete(searchKeyword) }
    }
  }

  companion object {
    private val searchKeywordComparator = object : DiffUtil.ItemCallback<SearchKeyword>() {
      override fun areItemsTheSame(oldItem: SearchKeyword, newItem: SearchKeyword): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: SearchKeyword, newItem: SearchKeyword): Boolean {
        return oldItem.keyword == newItem.keyword
      }
    }
  }
}