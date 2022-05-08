package dev.cesonha.televisioner.domain.entities

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("season") val season: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("summary") var summary: String,
    @SerializedName("image") var image: ImageData
)

data class ImageData(
    @SerializedName("medium") val mediumQualityUrl: String,
    @SerializedName("original") val originalUrl: String
)
