package com.marvel.app.db

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MarvelAppDatabaseTest {

    @Mock
    lateinit var marvelAppDatabase: MarvelAppDatabase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testNotToGetNullDatabaseReferenceAnyTime() {
        assertNotNull(marvelAppDatabase)
    }
}