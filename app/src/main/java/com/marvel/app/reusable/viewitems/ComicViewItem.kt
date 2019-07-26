package com.marvel.app.reusable.viewitems

import com.marvel.app.R
import com.marvel.app.databinding.ItemComicBinding
import com.marvel.app.model.ComicImageInterface
import com.marvel.app.model.ComicItemViewModel
import com.marvel.app.utilities.extensions.load
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

class ComicViewItem(
        val viewModel: ComicItemViewModel
) : BindingViewItem<ViewItemRepresentable, ItemComicBinding>(R.layout.item_comic, viewModel), ComicImageInterface {

    private lateinit var binding: ItemComicBinding

    override fun bind(binding: ItemComicBinding, viewItemPosition: Int) {
        this.binding = binding

        binding.viewModel = viewModel

        viewModel.setComicImageInterface(this)

        if (viewModel.comicImage.isEmpty()) {
            viewModel.getComicsBy(viewModel.comicItem.resourceURI)
        } else {
            setComicImage(viewModel.comicImage)
        }
    }

    override fun setComicImage(imageUrl: String) {
        this.binding.comicImageView.load(imageUrl)
    }
}