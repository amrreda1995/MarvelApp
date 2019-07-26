package com.marvel.app.ui.character_details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.databinding.ActivityCharacterDetailsBinding
import com.marvel.app.model.Character
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetailsActivity : BaseActivity() {

    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var binding: ActivityCharacterDetailsBinding

    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details)

        character = intent.extras!!.getParcelable("character")

        initViewModel()
        initRecyclerViewBuilder()
        setupObservers()
        setupListeners()
        bindModelToViews()
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

    private fun bindModelToViews() {
        binding.character = character
    }
}
