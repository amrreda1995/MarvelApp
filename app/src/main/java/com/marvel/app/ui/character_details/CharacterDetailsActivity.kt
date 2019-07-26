package com.marvel.app.ui.character_details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.databinding.ActivityCharacterDetailsBinding
import com.marvel.app.model.Character
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_character_details.*

class CharacterDetailsActivity : BaseActivity() {

    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var binding: ActivityCharacterDetailsBinding

    private lateinit var character: Character

    private lateinit var comicsRecyclerViewBuilder: RecyclerViewBuilder
    private lateinit var eventsRecyclerViewBuilder: RecyclerViewBuilder
    private lateinit var seriesRecyclerViewBuilder: RecyclerViewBuilder
    private lateinit var storiesRecyclerViewBuilder: RecyclerViewBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_details)

        character = intent.extras!!.getParcelable("character")

        initViewModel()
        initRecyclerViewBuilders()
        setupObservers()
        setupListeners()
        bindModelToViews()

        viewModel.bindComics(character.comics.items, ComicsType.COMICS)
        viewModel.bindComics(character.events.items, ComicsType.EVENTS)
        viewModel.bindComics(character.stories.items, ComicsType.STORIES)
        viewModel.bindComics(character.series.items, ComicsType.SERIES)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CharacterDetailsViewModel::class.java]
    }

    private fun initRecyclerViewBuilders() {
        comicsRecyclerViewBuilder = RecyclerViewBuilderFactory(comicsRecyclerView)
                .buildWithLinearLayout(isDataBindingEnabled = true, orientation = RecyclerView.HORIZONTAL)
                .bindViewItems(this, viewModel.comicsItemsObserver)
                .setPaginationEnabled(true)
                .onPaginate {

                }

        eventsRecyclerViewBuilder = RecyclerViewBuilderFactory(eventsRecyclerView)
                .buildWithLinearLayout(isDataBindingEnabled = true, orientation = RecyclerView.HORIZONTAL)
                .bindViewItems(this, viewModel.eventsItemsObserver)
                .setPaginationEnabled(true)
                .onPaginate {

                }

        storiesRecyclerViewBuilder = RecyclerViewBuilderFactory(storiesRecyclerView)
                .buildWithLinearLayout(isDataBindingEnabled = true, orientation = RecyclerView.HORIZONTAL)
                .bindViewItems(this, viewModel.storiesItemsObserver)
                .setPaginationEnabled(true)
                .onPaginate {

                }

        seriesRecyclerViewBuilder = RecyclerViewBuilderFactory(seriesRecyclerView)
                .buildWithLinearLayout(isDataBindingEnabled = true, orientation = RecyclerView.HORIZONTAL)
                .bindViewItems(this, viewModel.seriesItemsObserver)
                .setPaginationEnabled(true)
                .onPaginate {

                }
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
