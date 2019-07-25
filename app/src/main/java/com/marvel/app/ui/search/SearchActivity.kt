package com.marvel.app.ui.search

import android.os.Bundle
import com.marvel.app.R
import com.marvel.app.base.BaseActivity

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
