package com.marvel.app.model

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ComicItemViewModelTest {

    @Mock
    lateinit var comicItemViewModel: ComicItemViewModel

    @Mock
    lateinit var comicImageSetter: ComicImageSetter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        comicItemViewModel.setComicImageSetter(comicImageSetter)
    }

    @Test
    fun bindComicImageIfNotEmpty() {
        comicItemViewModel.comicImage = "test"

        comicItemViewModel.setComicItemImage()

        verify(comicImageSetter).setComicImage("test")
    }
}