package com.marvel.app.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.base.CharactersBaseActivity
import com.marvel.app.ui.characters.CharactersActivity
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : CharactersBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        characterViewItemType = CharacterViewItemType.VIEW_TYPE_2

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()
    }

    override fun setupListeners() {
        super.setupListeners()

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            val bool =  if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                currentOffset = 0
                pagesCount = 0
                startRecyclerViewBuilderLoading()
                viewModel.getCharacters(characterViewItemType = characterViewItemType, searchByName = searchEditText.text.toString(), clearsOnSet = true)

                true
            } else {
                false
            }

            bool
        }
    }
}
