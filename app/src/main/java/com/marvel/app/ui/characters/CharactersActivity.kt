package com.marvel.app.ui.characters

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.ui.characters.viewitems.FooterViewItem
import com.marvel.app.utilities.CompletableViewState
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : BaseActivity() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var recyclerViewBuilder: RecyclerViewBuilder

    private var pagesCount = 0
    private var currentOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()

        viewModel.getCharacters()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CharactersViewModel::class.java]
    }

    private fun initRecyclerViewBuilder() {
        recyclerViewBuilder = RecyclerViewBuilderFactory(charactersRecyclerViewList)
                .buildWithLinearLayout(isDataBindingEnabled = true)
                .setLoadingView(loadingTextView)
                .setEmptyView(noItemsTextView)
                .bindViewItems(this, viewModel.characterViewItemsObserver)
                .setPaginationEnabled(true)
                .onPaginate {
                    if (currentOffset == pagesCount - 1) {
                        recyclerViewBuilder.setPaginationEnabled(false)
                        recyclerViewBuilder.setFooter(null)
                        return@onPaginate
                    }

                    viewModel.getCharacters(++currentOffset)
                }
                .startLoading()
    }

    private fun setupObservers() {
        viewModel.pagesCount.observe(this, Observer {
            pagesCount = it
        })

        viewModel.charactersViewState.observe(this, Observer {
            when (it) {
                is CompletableViewState.Completed -> {
                    swipeRefreshLayout.isRefreshing = false

                    if (currentOffset < pagesCount) {
                        recyclerViewBuilder.setFooter(FooterViewItem())
                    }
                }

                is CompletableViewState.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                    loadingTextView.text = it.message.errorMessage
                }
            }
        })
    }

    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout.isRefreshing) {
                pagesCount = 0
                currentOffset = 0

                viewModel.getCharacters(clearsOnSet = true)
            }
        }

        searchButton.setOnClickListener {

        }

        loadingTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters(clearsOnSet = true)
        }

        noItemsTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters(clearsOnSet = true)
        }
    }

    private fun startRecyclerViewBuilderLoading() {
        loadingTextView.text = "Loading..."
        recyclerViewBuilder.startLoading()
    }
}
