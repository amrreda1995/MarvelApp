package com.marvel.app.ui.character_details

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.marvel.app.base.BaseActivity
import com.marvel.app.databinding.ActivityCharacterDetailsBinding
import com.marvel.app.model.Character
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_character_details.*
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.marvel.app.R
import com.marvel.app.utilities.extensions.toast

enum class CharacterUrlType(val value: String) {
    DETAIL("detail"), WIKI("wiki"), COMIC_LINK("comiclink")
}


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

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun bindModelToViews() {
        binding.character = character
    }

    fun openLink(view: View) {

        when(view.id) {
            R.id.detailsLayout -> searchOfUrlOfType(CharacterUrlType.DETAIL)
            R.id.wikiLayout -> searchOfUrlOfType(CharacterUrlType.WIKI)
            else -> searchOfUrlOfType(CharacterUrlType.COMIC_LINK)
        }
    }

    private fun searchOfUrlOfType(characterUrlType: CharacterUrlType) {
        val urlIndex = character.urls.indexOfFirst { it.type == characterUrlType.value }

        if (urlIndex > -1) {
            openLinkInBrowser(character.urls[urlIndex].url)
        } else {
            toast("No url found!")
        }
    }

    private fun openLinkInBrowser(url: String) {
        if (url.isNotEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } else {
            toast("No url found!")
        }
    }
}
