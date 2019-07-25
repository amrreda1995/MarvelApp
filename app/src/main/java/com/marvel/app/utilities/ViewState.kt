package com.marvel.app.utilities

import com.marvel.app.model.Message

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    class Success<out T>(val data: T): ViewState<T>()
    class Error(val message: Message): ViewState<Nothing>()
}

sealed class CompletableViewState {
    object Loading : CompletableViewState()
    object Completed : CompletableViewState()
    class Error(val message: Message): CompletableViewState()
}
