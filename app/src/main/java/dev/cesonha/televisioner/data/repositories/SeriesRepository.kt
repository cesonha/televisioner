package dev.cesonha.televisioner.data.repositories

import dev.cesonha.televisioner.data.datasources.SeriesDataSource
import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val datasource: SeriesDataSource
) : ISeriesRepository {

    override suspend fun getEpisodeDetails(episodeId: Int): Result<Episode> {
        return datasource.getEpisodeDetails(episodeId)
    }

    override suspend fun getSeriesList(page: Int): Result<List<Series>> {
        return datasource.getSeriesList(page)
    }

    override suspend fun searchSeries(query: String): Result<List<Series>> {
        return datasource.getSeriesFromQuery(query)
    }

    override suspend fun getSeriesDetails(seriesId: Int): Result<Series> {
        return datasource.getSeriesDetails(seriesId)
    }
}
