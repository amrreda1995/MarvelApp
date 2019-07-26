package com.marvel.app.reusable.viewitems

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.marvel.app.databinding.ItemCharacter1Binding
import com.marvel.app.databinding.ItemCharacter2Binding
import com.marvel.app.model.CharacterViewModel
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

enum class CharacterViewItemType {
    VIEW_TYPE_1, VIEW_TYPE_2
}

class CharacterViewItem(
        private val viewModel: CharacterViewModel
) : BindingViewItem<ViewItemRepresentable, ViewDataBinding>(viewModel.layoutResource, viewModel) {

    override fun hashCode(): Int {
        return viewModel.character.id
    }

    override fun bind(binding: ViewDataBinding, viewItemPosition: Int) {

        if (binding is ItemCharacter1Binding)
            binding.viewModel = viewModel
        else if (binding is ItemCharacter2Binding)
            binding.viewModel = viewModel

        Log.d("okkk", viewModel.character.comics.items[0].resourceURI + "what!")
    }
}