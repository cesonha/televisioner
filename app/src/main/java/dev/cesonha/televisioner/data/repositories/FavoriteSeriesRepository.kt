package dev.cesonha.televisioner.data.repositories

import dev.cesonha.televisioner.data.datasources.FavoriteSeriesDataSource
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.IFavoriteSeriesRepository
import javax.inject.Inject

class FavoriteSeriesRepository @Inject constructor(
    private val datasource: FavoriteSeriesDataSource
) : IFavoriteSeriesRepository {

    override suspend fun getFavoriteSeries(): Result<List<Series>> {
        return datasource.getFavoriteSeries()
    }

    override suspend fun addFavoriteSeries(series: Series): Result<Unit> {
        return datasource.addFavoriteSeries(series)
    }

    override suspend fun removeFavoriteSeries(series: Series): Result<Unit> {
        return datasource.removeFavoriteSeries(series)
    }

    override suspend fun isFavoriteSeries(seriesId: Int): Result<Boolean> {
        return datasource.isFavoriteSeries(seriesId)
    }
}
