package com.marvel.app.reusable.viewitems

import com.marvel.app.R
import com.marvel.app.databinding.ItemComicBinding
import com.marvel.app.model.ComicViewModel
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

class ComicViewItem(
        private val viewModel: ComicViewModel
) : BindingViewItem<ViewItemRepresentable, ItemComicBinding>(R.layout.item_comic, viewModel) {

    override fun bind(binding: ItemComicBinding, viewItemPosition: Int) {
        binding.viewModel = viewModel
    }
}