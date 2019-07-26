package com.marvel.app.ui.comic_images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marvel.app.R
import com.marvel.app.model.Comic
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.utilities.extensions.load
import kotlinx.android.synthetic.main.fragment_comic.view.*

class ComicFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_comic, container, false)

        val bundle = arguments

        val viewModel = bundle?.getParcelable<ComicItemViewModel>("viewModel")

        viewModel?.let {
            rootView.comicImage.load(viewModel.comicImage)
            rootView.titleTextView.text = viewModel.comicItem.name
        }

        return rootView
    }
}
