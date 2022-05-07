package dev.cesonha.televisioner.domain.repositories

import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series
import retrofit2.Response

interface ISeriesRepository {

    suspend fun getEpisodeDetails(episodeId: Int): Response<Episode>

    suspend fun getSeriesList(page: Int): Response<List<Series>>

    suspend fun getSeriesDetails(seriesId: Int): Response<Series>
}
