package dev.cesonha.televisioner.data.api

import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeService {

    @GET("shows")
    suspend fun getSeriesByPage(
        @Query("page") page: Int
    ): Response<List<Series>>

    @GET("episodes/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<Episode>

    @GET("shows/{series-id}")
    suspend fun getSeriesDetailsById(
        @Path("series-id") seriesId: Int,
        @Query("embed") embed: String = "episodes"
    ): Response<Series>
}
