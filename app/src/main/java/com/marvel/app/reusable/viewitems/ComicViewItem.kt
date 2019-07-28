package com.marvel.app.reusable.viewitems

import android.view.View
import android.widget.ImageView
import com.marvel.app.R
import com.marvel.app.model.ComicImageSetter
import com.marvel.app.model.ComicItemViewModel
import com.recyclerviewbuilder.library.ViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable
import com.squareup.picasso.Picasso
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

        if (viewModel.comicImage.isEmpty()) {
            Picasso.get().load(R.drawable.loading).into(itemView.comicImageView)
            viewModel.getComicData()
        } else {
            setComicImage(viewModel.comicImage)
        }
    }

    override fun setComicImage(comicImage: String) {
        Picasso.get().load(comicImage).into(comicImageView)
    }
}