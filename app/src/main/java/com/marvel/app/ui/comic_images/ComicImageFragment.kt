package com.marvel.app.ui.comic_images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marvel.app.R
import com.marvel.app.model.Comic
import com.marvel.app.utilities.extensions.load
import kotlinx.android.synthetic.main.fragment_comic_image.view.*

class ComicImageFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_comic_image, container, false)

        val bundle = arguments

        val comic = bundle?.getParcelable<Comic>("comic")

        comic?.let {
            rootView.comicImage.load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
            rootView.titleTextView.text = comic.title
        }

        return rootView
    }
}
