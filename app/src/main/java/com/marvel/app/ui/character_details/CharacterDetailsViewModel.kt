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
            CoroutineScope(Dispatchers.IO).launch {

                deleteAllData(comicsType.value)

                if (items.isNotEmpty()) {
                    items.forEach {
                        getComicDataBy(it, withComicsItemsObserver, comicsType)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        withComicsItemsObserver.value = ViewItemsObserver()
                    }
                }
            }
        } else {
            getComicsFromDatabase(withComicsItemsObserver, comicsType)
        }
    }

    private suspend fun getComicDataBy(
            comicItem: ComicItem,
            withComicsItemsObserver: MutableLiveData<ViewItemsObserver>,
            comicsType: ComicsType
    ) {

        apiRequestManager.execute(
                request = {
                    characterDetailsRepo.getComics(comicItem.resourceURI)
                },
                onSuccess = { response, headers ->
                    val comicItemViewModel = ComicItemViewModel(
                            id = response.data.results[0].id,
                            characterId = characterId,
                            comicName = comicItem.name,
                            comicsType = comicsType.value
                    )

                    if (response.data.results.isNotEmpty()) {
                        response.data.results[0].thumbnail?.let {
                            comicItemViewModel.comicImage =
                                    "${it.path}.${it.extension}"
                        } ?: run {
                            comicItemViewModel.comicImage = "https://www.wildhareboca.com/wp-content/uploads/sites/310/2018/03/image-not-available.jpg"
                        }
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        saveComicToDatabase(comicItemViewModel, comicsType)
                    }

                    withComicsItemsObserver.value = ViewItemsObserver(arrayListOf(comicItemViewModel.viewItem))
                }
        )
    }

    private suspend fun saveComicToDatabase(comicItemViewModel: ComicItemViewModel, comicsType: ComicsType) {
        val comicsList = comicsLocalRepo.getSavedComics(characterId, comicsType.value)
        if (comicsList.isEmpty()) {
            comicsLocalRepo.saveComic(ComicItemViewModel())
        }

        comicsLocalRepo.saveComic(comicItemViewModel)
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

    private suspend fun deleteAllData(comicsType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            comicsLocalRepo.deleteAllData(characterId, comicsType)
        }
    }
}