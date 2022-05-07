package dev.cesonha.televisioner.domain.entities

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    var summary: String,
    val image: ImageData
)

data class ImageData(
    @SerializedName("medium") val mediumQualityUrl: String,
    @SerializedName("original") val originalUrl: String
)
