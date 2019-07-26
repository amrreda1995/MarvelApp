package com.marvel.app.model

import com.google.gson.annotations.SerializedName

data class Message(
        @SerializedName("status")
        var errorMessage: String = "",
        var statusCode: Int = 0
)