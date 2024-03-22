package com.org.gamecatalog.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.gamecatalog.R
import com.org.gamecatalog.data.model.Favorite
import com.org.gamecatalog.databinding.ActivityFavoriteBinding
import com.org.gamecatalog.adapter.FavoriteAdapter
import com.org.gamecatalog.ui.base.BaseActivity
import com.org.gamecatalog.ui.base.UiState
import com.org.gamecatalog.ui.detailgame.DetailGameActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(R.layout.activity_favorite) {

  private val viewModel: FavoriteViewModel by viewModels()

  private val favoriteAdapter = FavoriteAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActionBar()
    setupListFavorite()
  }

  private fun setupListFavorite() {
    binding.emptyLayout.message = getString(R.string.txt_empty)

    binding.rvFavorite.apply {
      adapter = favoriteAdapter
      layoutManager = LinearLayoutManager(this@FavoriteActivity)
      setHasFixedSize(true)
    }

    favoriteAdapter.onItemDeleteListener = object : FavoriteAdapter.OnItemDeleteListener {
      override fun onItemDelete(favorite: Favorite) {
        viewModel.deleteFavorite(favorite)
      }
    }

    favoriteAdapter.onItemClickListener = object : FavoriteAdapter.OnItemClickListener {
      override fun onItemClick(favorite: Favorite) {
        startActivity(Intent(this@FavoriteActivity, DetailGameActivity::class.java).apply {
          putExtra(DetailGameActivity.EXTRA_GAME_ID, favorite.id)
        })
      }
    }

    lifecycleScope.launch {
      viewModel.listFavorite.collect {
        when(it) {
          is UiState.Loading -> {}
          is UiState.Success -> {
            binding.rvState = true
            binding.emptyLayout.emptyLayoutState = false
            favoriteAdapter.submitList(it.data)
          }
          is UiState.Empty -> {
            binding.rvState = false
            binding.emptyLayout.emptyLayoutState  = true
          }
          is UiState.Error -> {
            binding.rvState = false
            binding.emptyLayout.emptyLayoutState  = true
          }
        }
      }
    }
  }

  private fun setupActionBar() {
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    binding.toolbar.setNavigationOnClickListener {
      onBackPressedDispatcher.onBackPressed()
    }
  }

  companion object {
    fun newInstance(context: Context, intent: Intent) {
      context.startActivity(intent)
    }
  }
}