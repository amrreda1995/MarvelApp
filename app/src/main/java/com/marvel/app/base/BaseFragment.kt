package com.marvel.app.base

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marvel.app.app.MarvelApplication
import com.marvel.app.di.ApplicationComponent
import javax.inject.Inject

open class BaseFragment : Fragment() {

    val component: ApplicationComponent
        get() = (activity?.application as MarvelApplication).component

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appResources: Resources
}