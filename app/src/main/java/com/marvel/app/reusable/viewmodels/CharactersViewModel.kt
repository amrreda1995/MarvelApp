package com.marvel.app.reusable.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.Character
import com.marvel.app.model.CharacterViewModel
import com.marvel.app.model.Message
import com.marvel.app.repositories.local.CharactersLocalRepoInterface
import com.marvel.app.repositories.remote.CharactersRemoteRepoInterface
import com.marvel.app.reusable.viewitems.CharacterViewItemType
import com.marvel.app.utilities.CompletableViewState
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.InternetConnectionManager
import com.marvel.app.utilities.managers.InternetConnectionManagerInterface
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val charactersRemoteRepo: CharactersRemoteRepoInterface,
        private val charactersLocalRepo: CharactersLocalRepoInterface,
        private val internetConnectionManager: InternetConnectionManagerInterface
) : ViewModel() {

    val characterViewItemsObserver = MutableLiveData<ViewItemsObserver>()
    val charactersViewState = MutableLiveData<CompletableViewState>()
    val pagesCount = MutableLiveData<Int>()

    val toggleRecyclerViewBuilderPagination = MutableLiveData<Boolean>()

    fun getCharacters(
            searchByName: String? = null,
            offset: Int = 0,
            clearsOnSet: Boolean,
            characterViewItemType: CharacterViewItemType
    ) {
        if (internetConnectionManager.isConnectedToInternet) {
            apiRequestManager.execute(
                    request = {
                        charactersRemoteRepo.getCharacters(searchByName, offset)
                    },
                    onSuccess = { response, _ ->
                        pagesCount.value = response.data.total

                        bindCharactersToRecyclerView(response.data.results, characterViewItemType, clearsOnSet)

                        CoroutineScope(Dispatchers.IO).launch {
                            if (clearsOnSet) {
                                deleteAllData()
                            }

                            saveCharactersToDatabase(response.data.results)
                        }

                        charactersViewState.value = CompletableViewState.Completed
                    },
                    onFailure = {
                        charactersViewState.value = CompletableViewState.Error(it)
                    }
            )
        } else {
            toggleRecyclerViewBuilderPagination.value = false
            getCharactersFromDatabase(characterViewItemType, clearsOnSet)
        }
    }

    private suspend fun saveCharactersToDatabase(characters: List<Character>) {
        characters.forEach { charactersLocalRepo.saveCharacter(it) }
    }

    private fun getCharactersFromDatabase(
            characterViewItemType: CharacterViewItemType,
            clearsOnSet: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val characters = charactersLocalRepo.getSavedCharacter()

            withContext(Dispatchers.Main) {
                if (characters.isNotEmpty()) {
                    bindCharactersToRecyclerView(characters, characterViewItemType, clearsOnSet)
                } else {
                    charactersViewState.value = CompletableViewState.Error(Message("No internet connection!"))
                }
            }
        }
    }

    private suspend fun deleteAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            charactersLocalRepo.deleteAllData()
        }
    }

    private fun bindCharactersToRecyclerView(
            characters: List<Character>,
            characterViewItemType: CharacterViewItemType,
            clearsOnSet: Boolean
    ) {
        characterViewItemsObserver.value =
                ViewItemsObserver(
                        characters.map {
                            CharacterViewModel(it, characterViewItemType).viewItem
                        }.toArrayList(),
                        clearsOnSet = clearsOnSet
                )
    }
}