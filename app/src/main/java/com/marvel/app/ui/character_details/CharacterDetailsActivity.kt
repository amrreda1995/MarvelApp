package com.marvel.app.ui.character_details

import android.os.Bundle
import com.marvel.app.R
import com.marvel.app.base.BaseActivity

class CharacterDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
    }
}
