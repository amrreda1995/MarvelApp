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
import com.marvel.app.utilities.managers.InternetConnectionManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class CharactersViewModel @Inject constructor(
        var apiRequestManager: ApiRequestManagerInterface,
        private val charactersRemoteRepo: CharactersRemoteRepoInterface,
        private val charactersLocalRepo: CharactersLocalRepoInterface,
        var internetConnectionManager: InternetConnectionManagerInterface
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
                    searchByName?.let {
                        charactersRemoteRepo.getCharactersBy(searchByName, offset)
                    } ?: run {
                        charactersRemoteRepo.getCharacters(offset)
                    }
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

            searchByName?.let {
                searchForCharactersBy(it)
            } ?: run {
                getCharactersFromDatabase(characterViewItemType, clearsOnSet)
            }
        }
    }

    private suspend fun saveCharactersToDatabase(characters: List<Character>) {
        val charactersList = charactersLocalRepo.getSavedCharacter()
        if (charactersList.isEmpty()) {
            charactersLocalRepo.saveCharacter(Character())
        }

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

    private fun searchForCharactersBy(characterName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val characters = charactersLocalRepo.searchForCharactersBy(characterName)

            withContext(Dispatchers.Main) {
                bindCharactersToRecyclerView(characters, CharacterViewItemType.VIEW_TYPE_2, true)
            }
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