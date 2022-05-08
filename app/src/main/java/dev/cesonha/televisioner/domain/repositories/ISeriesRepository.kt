package dev.cesonha.televisioner.domain.repositories

import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series

interface ISeriesRepository {

    suspend fun getEpisodeDetails(episodeId: Int): Result<Episode>

    suspend fun getSeriesList(page: Int): Result<List<Series>>

    suspend fun searchSeries(query: String): Result<List<Series>>

    suspend fun getSeriesDetails(seriesId: Int): Result<Series>
}
