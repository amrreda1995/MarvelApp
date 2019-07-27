package com.marvel.app.ui.comics_preview

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.repositories.local.ComicsLocalRepoInterface
import com.marvel.app.repositories.remote.CharacterDetailsRepoInterface
import com.marvel.app.utilities.extensions.toArrayList
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_comics_preview.*
import javax.inject.Inject

class ComicsPreviewActivity : BaseActivity() {

    @Inject
    lateinit var apiRequestManager: ApiRequestManagerInterface

    @Inject
    lateinit var characterDetailsRepo: CharacterDetailsRepoInterface

    @Inject
    lateinit var comicsLocalRepo: ComicsLocalRepoInterface

    private lateinit var viewModels: ArrayList<ComicItemViewModel>

    private lateinit var recyclerViewBuilder: RecyclerViewBuilder

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics_preview)

        viewModels = intent.extras!!.getParcelableArrayList("viewModels")
        position = intent.extras!!.getInt("position")

        numbersTextView.text = "${position + 1}/${viewModels.size}"

        initRecyclerViewBuilder()
        setupListeners()

        recyclerViewBuilder.scrollTo(position, false)
    }

    private fun initRecyclerViewBuilder() {
        recyclerViewBuilder = RecyclerViewBuilderFactory(comicsRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .setViewItems(viewModels.map {
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
                }.toArrayList())
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }

        comicsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                numbersTextView.text = "${position + 1}/${viewModels.size}"
            }
        })
    }
}
