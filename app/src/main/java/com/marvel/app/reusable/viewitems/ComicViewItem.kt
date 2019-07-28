package com.marvel.app.reusable.viewitems

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.marvel.app.model.ComicImageSetter
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.utilities.extensions.load
import com.recyclerviewbuilder.library.ViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable
import kotlinx.android.synthetic.main.layout_comic_image.view.*
import kotlinx.android.synthetic.main.layout_comic_title.view.*

class ComicViewItem(
        val viewModel: ComicItemViewModel
) : ViewItem<ViewItemRepresentable>(viewModel.layoutResource, viewModel), ComicImageSetter {

    private lateinit var comicImageView: ImageView

    override fun hashCode(): Int {
        return viewModel.resourceURI.hashCode()
    }

    init {
        viewModel.setComicImageSetter(this)
    }

    override fun bind(itemView: View, viewItemPosition: Int) {
        comicImageView = itemView.comicImageView

        itemView.titleTextView.text = viewModel.comicName

        viewModel.setComicItemImage()
    }

    override fun setComicImage(comicImage: String) {
        comicImageView.load(comicImage)
    }
}