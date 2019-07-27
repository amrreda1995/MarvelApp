package com.marvel.app.ui.character_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.ComicItem
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.repositories.local.ComicsLocalRepoInterface
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.InternetConnectionManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

enum class ComicsType(val value: String) {
    COMICS("COMICS"), EVENTS("EVENTS"),
    STORIES("STORIES"), SERIES("SERIES")
}

class CharacterDetailsViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface,
        private val comicsLocalRepo: ComicsLocalRepoInterface,
        private val internetConnectionManager: InternetConnectionManagerInterface
) : ViewModel() {

    val comicsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val eventsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val seriesItemsObserver = MutableLiveData<ViewItemsObserver>()
    val storiesItemsObserver = MutableLiveData<ViewItemsObserver>()

    var characterId = 0

    fun bindComicsOf(
            items: List<ComicItem>,
            withComicsItemsObserver: MutableLiveData<ViewItemsObserver>,
            comicsType: ComicsType
    ) {
        if (internetConnectionManager.isConnectedToInternet) {

            deleteAllData(comicsType.value)

            saveComicToDatabase(items, comicsType)

            withComicsItemsObserver.value = ViewItemsObserver(
                    items.map {
                        ComicItemViewModel(
                                characterId = characterId,
                                comicName = it.name,
                                resourceURI = it.resourceURI,
                                comicsType = comicsType.value,
                                apiRequestManager = apiRequestManager,
                                characterDetailsRepo = characterDetailsRepo,
                                comicsLocalRepo = comicsLocalRepo
                        ).viewItem
                    }.toArrayList()
            )
        } else {
            getComicsFromDatabase(withComicsItemsObserver, comicsType)
        }
    }

    private fun saveComicToDatabase(items: List<ComicItem>, comicsType: ComicsType) {
        CoroutineScope(Dispatchers.IO).launch {
            val comicsList = comicsLocalRepo.getSavedComics(characterId, comicsType.value)
            if (comicsList.isEmpty()) {
                comicsLocalRepo.saveComic(ComicItemViewModel())
            }

            items.forEach {
                comicsLocalRepo.saveComic(
                        ComicItemViewModel(
                                characterId = characterId,
                                comicName = it.name,
                                resourceURI = it.resourceURI,
                                comicsType = comicsType.value
                        )
                )
            }
        }
    }

    private fun getComicsFromDatabase(withComicsItemsObserver: MutableLiveData<ViewItemsObserver>, comicsType: ComicsType) {
        CoroutineScope(Dispatchers.IO).launch {
            val comics = comicsLocalRepo.getSavedComics(characterId, comicsType.value)

            withContext(Dispatchers.Main) {
                if (comics.isNotEmpty()) {
                    withComicsItemsObserver.value = ViewItemsObserver(
                            comics.map {
                                it.viewItem
                            }.toArrayList()
                    )
                } else {
                    withComicsItemsObserver.value = ViewItemsObserver()
                }
            }
        }
    }

    private fun deleteAllData(comicsType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            comicsLocalRepo.deleteAllData(characterId, comicsType)
        }
    }
}