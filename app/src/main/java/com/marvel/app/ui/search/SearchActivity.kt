package com.marvel.app.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.marvel.app.R
import com.marvel.app.base.CharactersBaseActivity
import com.marvel.app.reusable.viewitems.CharacterViewItemType
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.*

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
            val bool = if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (searchEditText.toString().trim().isNotEmpty()) {
                    getCharacters()
                    closeKeypad()
                }

                true
            } else {
                false
            }

            bool
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            private var delayed = false

            override fun afterTextChanged(s: Editable?) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (!delayed) {
                        delayed = true
                        delay(1500)

                        withContext(Dispatchers.Main) {
                            delayed = false

                            if (s.toString().trim().isNotEmpty()) {
                                getCharacters()
                            }
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

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
                searchByName = searchEditText.text.toString().trim(),
                clearsOnSet = true
        )
    }

    private fun closeKeypad() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}
