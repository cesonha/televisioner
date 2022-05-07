package dev.cesonha.televisioner.domain.repositories

import dev.cesonha.televisioner.domain.entities.Series

interface IFavoriteSeriesRepository {

    suspend fun getFavoriteSeries(): List<Series>

    suspend fun addFavoriteSeries(series: Series)

    suspend fun removeFavoriteSeries(series: Series)

    suspend fun isFavoriteSeries(seriesId: Int): Boolean
}
