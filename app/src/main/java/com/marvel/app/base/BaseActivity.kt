package com.marvel.app.base

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.marvel.app.app.MarvelApplication
import com.marvel.app.di.ApplicationComponent
import javax.inject.Inject

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val component: ApplicationComponent
        get() = (application as MarvelApplication).component

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appResources: Resources
}