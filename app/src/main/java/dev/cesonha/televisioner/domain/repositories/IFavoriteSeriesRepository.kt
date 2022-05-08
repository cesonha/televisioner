package dev.cesonha.televisioner.domain.repositories

import dev.cesonha.televisioner.domain.entities.Series

interface IFavoriteSeriesRepository {

    suspend fun getFavoriteSeries(): Result<List<Series>>

    suspend fun addFavoriteSeries(series: Series): Result<Unit>

    suspend fun removeFavoriteSeries(series: Series): Result<Unit>

    suspend fun isFavoriteSeries(seriesId: Int): Result<Boolean>
}
