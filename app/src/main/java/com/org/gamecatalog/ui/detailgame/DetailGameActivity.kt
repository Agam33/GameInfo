package com.org.gamecatalog.ui.detailgame

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.org.gamecatalog.R
import com.org.gamecatalog.data.model.Favorite
import com.org.gamecatalog.databinding.ActivityDetailGameBinding
import com.org.gamecatalog.ui.base.BaseActivity
import com.org.gamecatalog.ui.base.UiState
import com.org.gamecatalog.ui.util.addSuffixAndClickAble
import com.org.gamecatalog.ui.util.clickAble
import com.org.gamecatalog.ui.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class DetailGameActivity: BaseActivity<ActivityDetailGameBinding>(R.layout.activity_detail_game) {

  private val viewModel: DetailGameViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupActionBar()
    setupViewModel()
    setupDetailGame()
  }

  private fun setupDetailGame() {
    lifecycleScope.launch {
      viewModel.detailGameUiState.collect {
        when(it) {
          is UiState.Success -> {
            binding.fabFavoriteState = true

            binding.detailGame = it.data
            binding.rating.detailGame = it.data
            binding.platform.detailGame = it.data

            binding.aboutGame.tvDescription.apply {
              text = it.data.description
              addSuffixAndClickAble(
                suffixText = getString(R.string.txt_read_more),
                maxLine = 8,
                onClick = {
                  MaterialAlertDialogBuilder(this@DetailGameActivity, R.style.ThemeOverlay_App_DetailGameDialog)
                    .setTitle(getString(R.string.txt_about_game))
                    .setMessage(it.data.description)
                    .show()
                }
              )
            }

            binding.platform.tvWebsite.apply {
              text = it.data.websiteUrl
              clickAble { text ->
                val url = if(!text.startsWith("http://") && !text.startsWith("https://")) "https://$text"
                else text
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(i)
              }
            }

            setupFavoriteState()
            setupFabFavorite(it.data)
          }
          is UiState.Loading -> {
            binding.fabFavoriteState = false
          }
          is UiState.Error -> {
            binding.fabFavoriteState = false
            showLongToast(it.message)
          }
          else -> {}
        }
      }
    }
  }

  private fun setupFabFavorite(data: DetailGameItemUiState) {
    binding.fabFavorite.setOnClickListener {
      viewModel.setFavorite(
        Favorite(id = data.id, name = data.name, rating = data.rating, backgroundImage = data.backgroundImage, isFavorite = true, LocalDateTime.now())
      )
    }
  }

  private fun setupFavoriteState() {
    lifecycleScope.launch {
      viewModel.isFavorite.collect { isFavorite ->
        binding.fabFavorite.setImageResource(
          if(isFavorite) R.drawable.baseline_favorite_red_24
          else R.drawable.baseline_favorite_24
        )
      }
    }
  }

  private fun setupViewModel() {
    val gameId = intent.getIntExtra(EXTRA_GAME_ID, 0)
    viewModel.getDetailGame(gameId)
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
    const val EXTRA_GAME_ID = "extra-game-id"
    private const val TAG = "DetailGameActivity"
  }
}