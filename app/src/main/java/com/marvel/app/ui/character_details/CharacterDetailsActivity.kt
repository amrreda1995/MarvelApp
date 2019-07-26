package com.marvel.app.ui.character_details

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetailsActivity : BaseActivity() {

    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CharacterDetailsViewModel::class.java]
    }

    private fun initRecyclerViewBuilder() {

    }

    private fun setupObservers() {

    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }
}
