package dev.cesonha.televisioner.data.datasources

import dev.cesonha.televisioner.data.api.TvMazeService
import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.entities.SearchResult
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.exceptions.HttpRequestException
import dev.cesonha.televisioner.domain.exceptions.NullBodyException
import retrofit2.Response
import javax.inject.Inject

class SeriesDataSource @Inject constructor(
    private val service: TvMazeService
) {

    suspend fun getEpisodeDetails(episodeId: Int): Result<Episode> {
        val response: Response<Episode>

        try {
            response = service.getEpisodeById(episodeId)
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }

        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
            return Result.failure(NullBodyException())
        } else {
            return Result.failure(HttpRequestException(response.code()))
        }
    }

    suspend fun getSeriesList(page: Int): Result<List<Series>> {
        val response: Response<List<Series>>

        try {
            response = service.getSeriesByPage(page)
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
            return Result.failure(NullBodyException())
        } else {
            return Result.failure(HttpRequestException(response.code()))
        }
    }

    suspend fun getSeriesFromQuery(query: String): Result<List<Series>> {
        val response: Response<List<SearchResult>>

        try {
            response = service.getSeriesWithQuery(query)
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it.map { searchResult -> searchResult.series })
            }
            return Result.failure(NullBodyException())
        } else {
            return Result.failure(HttpRequestException(response.code()))
        }
    }

    suspend fun getSeriesDetails(seriesId: Int): Result<Series> {
        val response: Response<Series>

        try {
            response = service.getSeriesDetailsById(seriesId)
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }

        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
            return Result.failure(NullBodyException())
        } else {
            return Result.failure(HttpRequestException(response.code()))
        }
    }
}
