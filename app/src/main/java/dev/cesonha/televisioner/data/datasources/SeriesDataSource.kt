package dev.cesonha.televisioner.data.datasources

import dev.cesonha.televisioner.data.api.TvMazeService
import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.Series
import retrofit2.Response
import javax.inject.Inject

class SeriesDataSource @Inject constructor(
    private val service: TvMazeService
) {

    suspend fun getEpisodeDetails(episodeId: Int): Response<Episode> {
        return service.getEpisodeById(episodeId)
    }

    suspend fun getSeriesList(page: Int): Response<List<Series>> {
        return service.getSeriesByPage(page)
    }

    suspend fun getSeriesDetails(seriesId: Int): Response<Series> {
        return service.getSeriesDetailsById(seriesId)
    }
}
