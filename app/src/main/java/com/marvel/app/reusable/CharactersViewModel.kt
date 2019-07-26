package com.marvel.app.reusable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.CharacterViewModel
import com.marvel.app.repositories.CharactersRepoInterface
import com.marvel.app.ui.characters.viewitems.CharacterViewItemType
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val charactersRepo: CharactersRepoInterface
) : ViewModel() {

    val characterViewItemsObserver = MutableLiveData<ViewItemsObserver>()
    val charactersViewState = MutableLiveData<CompletableViewState>()
    val pagesCount = MutableLiveData<Int>()

    fun getCharacters(searchByName: String? = null, offset: Int = 0, clearsOnSet: Boolean = false, characterViewItemType: CharacterViewItemType) {
        apiRequestManager.execute(
                request = {
                    charactersRepo.getCharacters(searchByName, offset)
                },
                onSuccess = { response, _ ->

                    pagesCount.value = response.data.count

                    characterViewItemsObserver.value =
                            ViewItemsObserver(
                                    response.data.results.map {
                                        CharacterViewModel(it, characterViewItemType).viewItem
                                    }.toArrayList(),
                                    clearsOnSet = clearsOnSet
                            )

                    charactersViewState.value = CompletableViewState.Completed
                },
                onFailure = {
                    charactersViewState.value = CompletableViewState.Error(it)
                }
        )
    }
}