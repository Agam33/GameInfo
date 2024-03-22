package com.org.gamecatalog.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView
import com.org.gamecatalog.R
import com.org.gamecatalog.adapter.GameAdapter
import com.org.gamecatalog.adapter.ItemSearchKeywordAdapter
import com.org.gamecatalog.adapter.ItemSearchResultAdapter
import com.org.gamecatalog.customview.GameChipGroup
import com.org.gamecatalog.data.model.Game
import com.org.gamecatalog.data.model.SearchKeyword
import com.org.gamecatalog.databinding.ActivityMainBinding
import com.org.gamecatalog.ui.base.BaseActivity
import com.org.gamecatalog.ui.base.UiState
import com.org.gamecatalog.ui.detailgame.DetailGameActivity
import com.org.gamecatalog.ui.favorite.FavoriteActivity
import com.org.gamecatalog.util.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

  private val viewModel: HomeViewModel by viewModels()

  @Inject lateinit var gameAdapter: GameAdapter
  @Inject lateinit var itemSearchResultAdapter: ItemSearchResultAdapter
  @Inject lateinit var searchKeywordAdapter: ItemSearchKeywordAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupViewModel()
    setupOnBackPressed()
    setupListGenre()
    setupListGame()
    setupListSearchKeyword()
    setupRefreshLayout()
    setupSearchView()
  }

  private fun setupListSearchKeyword() {
    viewModel.getListSearchKeyword()

    binding.rvSearchKeyword.apply {
      adapter = searchKeywordAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
      setHasFixedSize(true)
    }

    lifecycleScope.launch {
      viewModel.searchKeywordState.collect {
        searchKeywordAdapter.submitList(it)
      }
    }

    searchKeywordAdapter.onKeywordClickListener = object : ItemSearchKeywordAdapter.OnKeywordClickListener {
      override fun onItemKeyword(searchKeyword: SearchKeyword) {
        binding.searchView.setText(searchKeyword.keyword)
        viewModel.searchGame(searchKeyword.keyword)
        viewModel.insertSearchKeyword(SearchKeyword(searchKeyword.keyword, LocalDateTime.now()))
      }
    }

    searchKeywordAdapter.onDeleteClickListener = object : ItemSearchKeywordAdapter.OnDeleteClickListener {
      override fun onItemDelete(searchKeyword: SearchKeyword) {
        viewModel.deleteSearchKeyword(searchKeyword)
      }
    }
  }

  private fun setupSearchView() = with(binding) {
    setSupportActionBar(binding.searchBar)

    binding.searchBar.setNavigationOnClickListener {
      searchView.show()
    }

    searchView.setupWithSearchBar(searchBar)

    progressBar.isIndeterminate = true
    tvNoResultState = true

    rvSearchResult.apply {
      adapter = itemSearchResultAdapter
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this@MainActivity, 4)
    }

    searchView.editText.setOnEditorActionListener { view, _, _ ->
      val query = view.text.toString()
      viewModel.searchGame(query)
      viewModel.insertSearchKeyword(SearchKeyword(query, LocalDateTime.now()))
      false
    }

    searchView.addTransitionListener { _, previousState, _ ->
      when(previousState) {
        SearchView.TransitionState.HIDDEN -> {
          rvResultState = false
          tvNoResultState = false
          rvSearchKeywordState = true
        }
        SearchView.TransitionState.HIDING -> {}
        SearchView.TransitionState.SHOWING -> {}
        SearchView.TransitionState.SHOWN -> {}
      }
    }

    lifecycleScope.launch {
      itemSearchResultAdapter.loadStateFlow.collectLatest {
        loadingState = it.refresh is LoadState.Loading
        rvResultState = it.refresh !is LoadState.Loading && itemSearchResultAdapter.itemCount >= 1
        tvNoResultState = it.refresh !is LoadState.Loading && itemSearchResultAdapter.itemCount < 1 && it.append.endOfPaginationReached
        rvSearchKeywordState = it.refresh !is LoadState.Loading && itemSearchResultAdapter.itemCount < 1 && it.append.endOfPaginationReached
      }
    }

    lifecycleScope.launch {
      viewModel.searchGameState.collectLatest {
        itemSearchResultAdapter.submitData(it)
      }
    }

    itemSearchResultAdapter.onItemClickListener = object : ItemSearchResultAdapter.OnItemClickListener {
      override fun onItemClicked(game: Game) {
        DetailGameActivity.newInstance(this@MainActivity, Intent(this@MainActivity, DetailGameActivity::class.java).apply {
          putExtra(DetailGameActivity.EXTRA_GAME_ID, game.id)
        })
      }
    }
  }

  private fun setupViewModel() {
    viewModel.getListGenre()
    viewModel.getListGame(null)
  }

  private fun setupRefreshLayout() = with(binding) {
    swipeRefreshLayout.setOnRefreshListener {
      setupViewModel()
      gameAdapter.refresh()
      chipGroup.refreshCheck()
      swipeRefreshLayout.isRefreshing = false
    }
  }

  private fun setupListGame() = with(binding) {
    gameAdapter.addLoadStateListener { loadState ->
      if(loadState.refresh is LoadState.Loading) {
        listGameProgressBar.isVisible = true
        rvGames.isVisible = false
        errorLayout.isVisible = false
      } else {
        val error = when {
          loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
          loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
          loadState.append is LoadState.Error -> loadState.append as LoadState.Error
          else -> null
        }

        if(error != null) {
          listGameProgressBar.isVisible = false
          rvGames.isVisible = false
          errorLayout.isVisible = true
        } else {
          listGameProgressBar.isVisible = false
          rvGames.isVisible = true
          errorLayout.isVisible = false
        }
      }
    }

    lifecycleScope.launch {
      viewModel.listGameState.collectLatest { data ->
        gameAdapter.submitData(data)
      }
    }

    rvGames.apply {
      adapter = gameAdapter
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this@MainActivity, MAX_GRID_SPAN)
    }

    gameAdapter.onItemClickListener = object : GameAdapter.OnItemClickListener {
      override fun onItemClicked(game: Game) {
        DetailGameActivity.newInstance(this@MainActivity, Intent(this@MainActivity, DetailGameActivity::class.java).apply {
          putExtra(DetailGameActivity.EXTRA_GAME_ID, game.id)
        })
      }
    }
  }

  private fun setupListGenre() = with(binding) {
    chipGroup.onItemChipClickListener = object : GameChipGroup.OnItemChipClickListener {
      override fun onChipClicked(chipModel: GameChipGroup.ChipModel) {
        viewModel.getListGame(chipGroup.ifFirstChip(chipModel.slug))
        gameAdapter.refresh()
        showShortToast(chipModel.title)
      }
    }

    lifecycleScope.launch {
      viewModel.listGenreState.collect { uiState ->
        when(uiState) {
          is UiState.Loading -> {}
          is UiState.Success -> {
            chipGroup.submit(uiState.data.map { genre ->
              chipGroup.toChipModel(genre.id, genre.name ?: "-", genre.slug ?: "")
            })
          }
          is UiState.Error -> {
            chipGroup.clear()
          }
          else -> {}
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean =
    when(item.itemId) {
      R.id.menu_favorite -> {
        FavoriteActivity.newInstance(this@MainActivity, Intent(this@MainActivity, FavoriteActivity::class.java))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }

  private fun setupOnBackPressed() {
    onBackPressedDispatcher.addCallback(this) {
      if(binding.searchView.isShowing) {
        binding.searchView.hide()
      } else {
        finish()
      }
    }
  }

  companion object {
    private const val MAX_GRID_SPAN = 2
  }
}