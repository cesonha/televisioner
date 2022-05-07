package dev.cesonha.televisioner.domain.entities

import com.google.gson.annotations.SerializedName

data class Series(
    val id: Int,
    val name: String,
    @SerializedName("image") val imageData: ImageData,
    @SerializedName("schedule") val scheduleData: ScheduleData,
    val genres: List<String>,
    var summary: String,
    @SerializedName("_embedded") val episodesData: EpisodeData
) {
    constructor(id: Int, name: String, posterUrl: String) : this(
        id, name, ImageData(posterUrl, posterUrl), ScheduleData(listOf()),
        listOf(), "", EpisodeData(listOf())
    )
}

data class EpisodeData(val episodes: List<Episode>)

data class ScheduleData(val days: List<String>)
