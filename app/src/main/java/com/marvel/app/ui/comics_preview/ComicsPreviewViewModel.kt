package com.marvel.app.ui.comics_preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.repositories.local.ComicsLocalRepoInterface
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.ViewItemsObserver
import java.util.ArrayList
import javax.inject.Inject

class ComicsPreviewViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val  characterDetailsRepo: CharacterDetailsRepoInterface,
        private val  comicsLocalRepo: ComicsLocalRepoInterface
): ViewModel() {

    val comicsViewItemsObserver = MutableLiveData<ViewItemsObserver>()

    fun setComicsViewModels(viewModels: ArrayList<ComicItemViewModel>) {
        comicsViewItemsObserver.value = ViewItemsObserver(
                viewModels.map {
                    val comicViewModel
                            = ComicItemViewModel(
                            characterId = it.characterId,
                            resourceURI = it.resourceURI,
                            comicName = it.comicName,
                            comicImage = it.comicImage,
                            comicsType = it.comicsType,
                            comicItemViewType = it.comicItemViewType,
                            apiRequestManager = apiRequestManager,
                            characterDetailsRepo = characterDetailsRepo,
                            comicsLocalRepo = comicsLocalRepo
                    )
                    comicViewModel.viewItem
                }.toArrayList()
        )
    }
}