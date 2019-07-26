package com.marvel.app.ui.character_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.ComicItem
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.repositories.CharacterDetailsRepoInterface
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import javax.inject.Inject

enum class ComicsType {
    COMICS, EVENTS, STORIES, SERIES
}

class CharacterDetailsViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface
) : ViewModel() {

    val comicsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val eventsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val seriesItemsObserver = MutableLiveData<ViewItemsObserver>()
    val storiesItemsObserver = MutableLiveData<ViewItemsObserver>()

    fun bindComics(items: List<ComicItem>, comicsType: ComicsType) {

        when(comicsType) {
            ComicsType.COMICS -> comicsItemsObserver.value = getViewItemObserverOf(items)
            ComicsType.EVENTS -> eventsItemsObserver.value = getViewItemObserverOf(items)
            ComicsType.STORIES -> storiesItemsObserver.value = getViewItemObserverOf(items)
            else ->  seriesItemsObserver.value = getViewItemObserverOf(items)
        }
    }

    private fun getViewItemObserverOf(items: List<ComicItem>): ViewItemsObserver {
        return ViewItemsObserver(
                items.map {
                    ComicItemViewModel(it, apiRequestManager, characterDetailsRepo).viewItem
                }.toArrayList()
        )
    }
}