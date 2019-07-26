package com.marvel.app.ui.characters

import android.content.Intent
import android.os.Bundle
import com.marvel.app.R
import com.marvel.app.base.CharactersBaseActivity
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import com.marvel.app.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_characters.*

open class CharactersActivity : CharactersBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        characterViewItemType = CharacterViewItemType.VIEW_TYPE_1

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()

        recyclerViewBuilder.startLoading()
        viewModel.getCharacters(characterViewItemType = CharacterViewItemType.VIEW_TYPE_1)
    }

    override fun setupListeners() {
        super.setupListeners()

        searchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
