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

class CharacterDetailsViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface
) : ViewModel() {

    val comicsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val eventsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val seriesItemsObserver = MutableLiveData<ViewItemsObserver>()
    val storeisItemsObserver = MutableLiveData<ViewItemsObserver>()

    val comicsViewState = MutableLiveData<CompletableViewState>()
    val eventsViewState = MutableLiveData<CompletableViewState>()
    val seriesViewState = MutableLiveData<CompletableViewState>()
    val storeisViewState = MutableLiveData<CompletableViewState>()

    fun getComicsBy(
            resourceUri: String,
            offset: Int = 0,
            viewState: MutableLiveData<CompletableViewState>,
            viewItemsObserver: MutableLiveData<ViewItemsObserver>,
            clearsOnSet: Boolean = false
    ) {
        viewState.value = CompletableViewState.Loading

        apiRequestManager.execute(
                request = {
                    characterDetailsRepo.getComics(resourceUri, offset)
                },
                onSuccess = { response, headers ->
                    viewState.value = CompletableViewState.Completed
                },
                onFailure = {
                    viewState.value = CompletableViewState.Error(it)
                }
        )
    }

    fun bindComics(items: List<ComicItem>) {
        comicsItemsObserver.value =
                ViewItemsObserver(
                        items.map {
                            ComicItemViewModel(it, apiRequestManager, characterDetailsRepo).viewItem
                        }.toArrayList()
                )
    }
}