package com.example.cyclistance.feature_main_screen.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HelpRequestDto(
    @SerializedName("accepted")
    val accepted: Boolean,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("id")
    val id: String
)