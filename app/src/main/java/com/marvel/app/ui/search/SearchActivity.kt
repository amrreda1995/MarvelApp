package com.marvel.app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.marvel.app.base.CharactersBaseActivity
import com.marvel.app.reusable.viewitems.CharacterViewItemType
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : CharactersBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(com.marvel.app.R.layout.activity_search)

        characterViewItemType = CharacterViewItemType.VIEW_TYPE_2

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()
    }

    override fun setupListeners() {
        super.setupListeners()

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            val bool = if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                getCharacters()
                closeKeypad()

                true
            } else {
                false
            }

            bool
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun getCharacters() {
        currentOffset = 0
        pagesCount = 0
        recyclerViewBuilder.setFooter(null)
        startRecyclerViewBuilderLoading()
        viewModel.getCharacters(
                characterViewItemType = characterViewItemType,
                searchByName = searchEditText.text.toString(),
                clearsOnSet = true
        )
    }

    private fun closeKeypad() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}
