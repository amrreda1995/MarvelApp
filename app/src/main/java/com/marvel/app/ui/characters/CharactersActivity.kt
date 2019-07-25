package com.marvel.app.ui.characters

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.utilities.CompletableViewState
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : BaseActivity() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var recyclerViewBuilder: RecyclerViewBuilder

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
//                    if (currentPage == totalPages) {
//                        recyclerViewBuilder.setPaginationEnabled(false)
//                        return@onPaginate
//                    }
//
//                    viewModel.getTopics()
                }
                .startLoading()
    }

    private fun setupObservers() {
        viewModel.charactersViewState.observe(this, Observer {
            when(it) {
                is CompletableViewState.Loading -> {

                }

                is CompletableViewState.Completed -> {

                }

                is CompletableViewState.Error -> {
                    loadingTextView.text = it.message.errorMessage
                }
            }
        })
    }

    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout.isRefreshing) {

            }
        }

        searchButton.setOnClickListener {

        }

        loadingTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters()
        }

        noItemsTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters()
        }
    }

    private fun startRecyclerViewBuilderLoading() {
        loadingTextView.text = "Loading..."
        recyclerViewBuilder.startLoading()
    }
}
