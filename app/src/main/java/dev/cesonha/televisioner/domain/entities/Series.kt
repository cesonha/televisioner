package dev.cesonha.televisioner.domain.entities

import com.google.gson.annotations.SerializedName

data class Series(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") var imageData: ImageData?,
    @SerializedName("schedule") val scheduleData: ScheduleData,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("summary") var summary: String?,
    @SerializedName("_embedded") val episodesData: EpisodeData
) {
    constructor(id: Int, name: String, posterUrl: String) : this(
        id, name, ImageData(posterUrl, posterUrl), ScheduleData(listOf(), ""),
        listOf(), "", EpisodeData(listOf())
    )
}

data class EpisodeData(@SerializedName("episodes") val episodes: List<Episode>)

data class ScheduleData(
    @SerializedName("days") val days: List<String>,
    @SerializedName("time") val time: String
)
