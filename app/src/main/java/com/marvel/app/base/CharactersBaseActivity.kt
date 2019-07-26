package com.marvel.app.base

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marvel.app.reusable.CharactersViewModel
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import com.marvel.app.ui.characters.viewitems.FooterViewItem
import com.marvel.app.utilities.CompletableViewState
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_characters.*
import kotlinx.android.synthetic.main.layout_loading_text_view.*
import kotlinx.android.synthetic.main.layout_no_items_text_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.*

@SuppressLint("Registered")
open class CharactersBaseActivity : BaseActivity() {

    protected lateinit var recyclerViewBuilder: RecyclerViewBuilder
    protected lateinit var viewModel: CharactersViewModel

    protected open var characterViewItemType = CharacterViewItemType.VIEW_TYPE_1

    protected var pagesCount = 0
    protected var currentOffset = 0

    protected fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CharactersViewModel::class.java]
    }

    protected fun initRecyclerViewBuilder() {
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

                viewModel.getCharacters(offset = ++currentOffset, characterViewItemType = characterViewItemType)
            }
    }

    protected open fun setupObservers() {
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

    protected open fun setupListeners() {
        loadingTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters(clearsOnSet = true, characterViewItemType = characterViewItemType)
        }

        noItemsTextView.setOnClickListener {
            startRecyclerViewBuilderLoading()
            viewModel.getCharacters(clearsOnSet = true, characterViewItemType = characterViewItemType)
        }
    }

    protected fun startRecyclerViewBuilderLoading() {
        loadingTextView.text = "Loading..."
        recyclerViewBuilder.startLoading()
    }
}
