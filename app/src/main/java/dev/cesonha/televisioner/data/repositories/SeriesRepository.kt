package dev.cesonha.televisioner.data.repositories

import dev.cesonha.televisioner.data.datasources.SeriesDataSource
import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import retrofit2.Response
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val datasource: SeriesDataSource
) : ISeriesRepository {

    override suspend fun getEpisodeDetails(episodeId: Int): Response<Episode> {
        return datasource.getEpisodeDetails(episodeId)
    }

    override suspend fun getSeriesList(page: Int): Response<List<Series>> {
        return datasource.getSeriesList(page)
    }

    override suspend fun getSeriesDetails(seriesId: Int): Response<Series> {
        return datasource.getSeriesDetails(seriesId)
    }
}
