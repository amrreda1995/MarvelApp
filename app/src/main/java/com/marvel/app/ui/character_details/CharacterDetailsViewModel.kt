package com.marvel.app.ui.character_details

import androidx.lifecycle.ViewModel
import com.marvel.app.repositories.CharacterDetailsRepoInterface
import com.marvel.app.utilities.managers.ApiRequestManagerInterface
import com.marvel.app.utilities.managers.SharedPreferencesManagerInterface
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
        private val apiRequestManager: ApiRequestManagerInterface,
        private val sharedPreferencesManager: SharedPreferencesManagerInterface,
        private val characterDetailsRepo: CharacterDetailsRepoInterface
) : ViewModel() {

}