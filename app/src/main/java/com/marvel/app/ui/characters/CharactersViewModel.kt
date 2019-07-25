package com.marvel.app.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.CharacterViewModel
import com.marvel.app.repositories.CharactersRepoInterface
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.InternetConnectionManagerInterface
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
        private val internetConnectionManager: InternetConnectionManagerInterface,
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val charactersRepo: CharactersRepoInterface
) : ViewModel() {

    val characterViewItemsObserver = MutableLiveData<ViewItemsObserver>()
    val charactersViewState = MutableLiveData<CompletableViewState>()

    fun getCharacters() {
        apiRequestManager.execute(
                request = {
                    charactersRepo.getCharacters()
                },
                onSuccess = { response, _ ->
                    characterViewItemsObserver.value =
                            ViewItemsObserver(
                                    response.data.results.map {
                                        CharacterViewModel(it).viewItem
                                    }.toArrayList()
                            )
                },
                onFailure = {
                    charactersViewState.value = CompletableViewState.Error(it)
                }
        )
    }
}