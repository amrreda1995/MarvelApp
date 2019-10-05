package com.marvel.app.ui.comics_preview

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.marvel.app.R
import com.marvel.app.base.BaseActivity
import com.marvel.app.model.ComicItemViewModel
import com.recyclerviewbuilder.library.RecyclerViewBuilder
import com.recyclerviewbuilder.library.RecyclerViewBuilderFactory
import kotlinx.android.synthetic.main.activity_comics_preview.*

class ComicsPreviewActivity : BaseActivity() {

    private lateinit var comicsViewModels: ArrayList<ComicItemViewModel>

    private lateinit var recyclerViewBuilder: RecyclerViewBuilder

    private lateinit var viewModel: ComicsPreviewViewModel

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics_preview)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ComicsPreviewViewModel::class.java]

        comicsViewModels = intent.extras!!.getParcelableArrayList("comicsViewModels")
        position = intent.extras!!.getInt("position")

        numbersTextView.text = "${position + 1}/${comicsViewModels.size}"

        initRecyclerViewBuilder()
        setupListeners()

        viewModel.setComicsViewModels(comicsViewModels)

        recyclerViewBuilder.scrollTo(position, false)
    }

    private fun initRecyclerViewBuilder() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(comicsRecyclerView)
        recyclerViewBuilder = RecyclerViewBuilderFactory(comicsRecyclerView)
                .buildWithLinearLayout(orientation = RecyclerView.HORIZONTAL)
                .bindViewItems(this, viewModel.comicsViewItemsObserver)
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }

        comicsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                numbersTextView.text = "${position + 1}/${comicsViewModels.size}"
            }
        })
    }
}
