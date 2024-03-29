package com.marvel.app.ui.character_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.databinding.ActivityCharacterDetailsBinding
import com.marvel.app.model.Character
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.model.ComicItemViewType
import com.marvel.app.reusable.viewitems.ComicViewItem
import com.marvel.app.ui.comics_preview.ComicsPreviewActivity
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.extensions.toast
import com.recyclerviewbuilder.library.BaseAdapterInterface
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_character_details.*
import java.util.ArrayList

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

        viewModel.characterId = character.id

        bindViews()
    }

    private fun bindViews() {
        viewModel.bindComicsOf(character.comics.items, viewModel.comicsItemsObserver, ComicsType.COMICS)
        viewModel.bindComicsOf(character.events.items, viewModel.eventsItemsObserver, ComicsType.EVENTS)
        viewModel.bindComicsOf(character.stories.items, viewModel.storiesItemsObserver, ComicsType.STORIES)
        viewModel.bindComicsOf(character.series.items, viewModel.seriesItemsObserver, ComicsType.SERIES)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[CharacterDetailsViewModel::class.java]
    }

    private fun initRecyclerViewBuilders() {
        comicsRecyclerViewBuilder = RecyclerViewBuilderFactory(comicsRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .setEmptyView(comicsNoItemsTextView)
                .bindViewItems(this, viewModel.comicsItemsObserver)

        eventsRecyclerViewBuilder = RecyclerViewBuilderFactory(eventsRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .setEmptyView(eventsNoItemsTextView)
                .bindViewItems(this, viewModel.eventsItemsObserver)

        storiesRecyclerViewBuilder = RecyclerViewBuilderFactory(storiesRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .setEmptyView(storiesNoItemsTextView)
                .bindViewItems(this, viewModel.storiesItemsObserver)

        seriesRecyclerViewBuilder = RecyclerViewBuilderFactory(seriesRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .setEmptyView(seriesNoItemsTextView)
                .bindViewItems(this, viewModel.seriesItemsObserver)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        comicsRecyclerViewBuilder.setOnItemClick { itemView, model, position ->
            startComicsActivity(comicsRecyclerView, position)
        }

        eventsRecyclerViewBuilder.setOnItemClick { itemView, model, position ->
            startComicsActivity(eventsRecyclerView, position)
        }

        storiesRecyclerViewBuilder.setOnItemClick { itemView, model, position ->
            startComicsActivity(storiesRecyclerView, position)
        }

        seriesRecyclerViewBuilder.setOnItemClick { itemView, model, position ->
            startComicsActivity(seriesRecyclerView, position)
        }
    }

    private fun startComicsActivity(recyclerView: RecyclerView, position: Int) {
        val viewModels = getViewModelsOf(recyclerView)

        startActivity(
                Intent(this, ComicsPreviewActivity::class.java)
                        .putParcelableArrayListExtra("comicsViewModels", viewModels)
                        .putExtra("position", position)
        )
    }

    private fun getViewModelsOf(recyclerView: RecyclerView): ArrayList<ComicItemViewModel> {
        return (recyclerView.adapter as BaseAdapterInterface).viewItemsArrayList.map {
            val viewModel = (it as ComicViewItem).viewModel
            viewModel.comicItemViewType = ComicItemViewType.COMIC_ITEM_2.value
            viewModel
        }.toArrayList()
    }

    private fun bindModelToViews() {
        binding.character = character
    }

    fun openLink(view: View) {

        when (view.id) {
            R.id.detailsLayout -> searchForUrlOfType(CharacterUrlType.DETAIL)
            R.id.wikiLayout -> searchForUrlOfType(CharacterUrlType.WIKI)
            else -> searchForUrlOfType(CharacterUrlType.COMIC_LINK)
        }
    }

    private fun searchForUrlOfType(characterUrlType: CharacterUrlType) {
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
