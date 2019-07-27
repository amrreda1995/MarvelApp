package com.marvel.app.ui.character_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.ComicItem
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class ComicsType {
    COMICS, EVENTS, STORIES, SERIES
}

class CharacterDetailsViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface
) : ViewModel() {

    val comicsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val eventsItemsObserver = MutableLiveData<ViewItemsObserver>()
    val seriesItemsObserver = MutableLiveData<ViewItemsObserver>()
    val storiesItemsObserver = MutableLiveData<ViewItemsObserver>()

    fun bindComicsOf(items: List<ComicItem>, withComicsItemsObserver: MutableLiveData<ViewItemsObserver>) {

        CoroutineScope(Dispatchers.IO).launch {

            if (items.isNotEmpty()) {
                items.forEach {
                    getComicDataBy(it, withComicsItemsObserver)
                }
            } else {
                withContext(Dispatchers.Main) {
                    withComicsItemsObserver.value = ViewItemsObserver()
                }
            }
        }
    }

    private suspend fun getComicDataBy(comicItem: ComicItem, withComicsItemsObserver: MutableLiveData<ViewItemsObserver>) {

        apiRequestManager.execute(
                request = {
                    characterDetailsRepo.getComics(comicItem.resourceURI)
                },
                onSuccess = { response, headers ->
                    val comicItemViewModel = ComicItemViewModel(comicItem)

                    if (response.data.results.isNotEmpty()) {
                        response.data.results[0].thumbnail?.let {
                            comicItemViewModel.comicImage =
                                    "${it.path}.${it.extension}"
                        } ?: run {
                            comicItemViewModel.comicImage = "https://www.wildhareboca.com/wp-content/uploads/sites/310/2018/03/image-not-available.jpg"
                        }
                    }

                    withComicsItemsObserver.value = ViewItemsObserver(arrayListOf(comicItemViewModel.viewItem))
                }
        )
    }
}