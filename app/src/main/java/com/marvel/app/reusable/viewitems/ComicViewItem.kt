package com.marvel.app.reusable.viewitems

import com.marvel.app.R
import com.marvel.app.databinding.ItemComicBinding
import com.marvel.app.model.ComicImageSetter
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.utilities.extensions.load
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

class ComicViewItem(
        val viewModel: ComicItemViewModel
) : BindingViewItem<ViewItemRepresentable, ItemComicBinding>(R.layout.item_comic, viewModel), ComicImageSetter {

    private lateinit var binding: ItemComicBinding

    override fun hashCode(): Int {
        return viewModel.resourceURI.hashCode()
    }

    override fun bind(binding: ItemComicBinding, viewItemPosition: Int) {
        this.binding = binding

        binding.comicImageView.setImageBitmap(null)

        binding.viewModel = viewModel

        viewModel.setComicImageSetter(this)

        viewModel.setComicItemImage()
    }

    override fun setComicImage(comicImage: String) {
        binding.comicImageView.load(comicImage)
    }
}