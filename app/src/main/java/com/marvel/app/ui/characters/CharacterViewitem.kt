package com.marvel.app.ui.characters

import android.util.Log
import com.marvel.app.R
import com.marvel.app.databinding.ItemCharacterBinding
import com.marvel.app.model.CharacterViewModel
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

class CharacterViewItem(
        private val viewModel: CharacterViewModel
): BindingViewItem<ViewItemRepresentable, ItemCharacterBinding>(R.layout.item_character, viewModel) {

    override fun hashCode(): Int {
        return viewModel.character.id
    }

    override fun bind(binding: ItemCharacterBinding, viewItemPosition: Int) {
        Log.d("image", viewModel.characterImage)
        binding.viewModel = viewModel
    }
}