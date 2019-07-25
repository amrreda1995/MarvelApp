package com.marvel.app.ui.characters.viewitems

import com.marvel.app.R
import com.marvel.app.databinding.ItemFooterBinding
import com.recyclerviewbuilder.library.BindingViewItem
import com.recyclerviewbuilder.library.ViewItemRepresentable

class FooterViewItem : BindingViewItem<ViewItemRepresentable, ItemFooterBinding>(R.layout.item_footer) {

    override fun hashCode(): Int {
        return 0
    }
}