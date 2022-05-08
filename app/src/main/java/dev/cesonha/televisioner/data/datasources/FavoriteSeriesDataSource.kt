package dev.cesonha.televisioner.data.datasources

import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import dev.cesonha.televisioner.data.room.entities.FavoriteSeries
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class FavoriteSeriesDataSource @Inject constructor(private val dao: FavoriteSeriesDao) {

    fun getFavoriteSeries(): Result<List<Series>> {
        return try {
            Result.success(dao.getFavoriteSeries().map { Series(it.seriesId, it.name, it.posterUrl) })
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }

    fun isFavoriteSeries(seriesId: Int): Result<Boolean> {
        return try {
            Result.success(dao.getSeriesFromFavorites(seriesId) != null)
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }

    fun addFavoriteSeries(favoriteSeries: Series): Result<Unit> {
        try {
            dao.addFavoriteSeries(
                FavoriteSeries(
                    favoriteSeries.id, favoriteSeries.id,
                    favoriteSeries.name, favoriteSeries.imageData.originalUrl
                )
            )
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }
        return Result.success(Unit)
    }

    fun removeFavoriteSeries(favoriteSeries: Series): Result<Unit> {
        try {
            dao.removeFavoriteSeries(
                FavoriteSeries(
                    favoriteSeries.id, favoriteSeries.id,
                    favoriteSeries.name, favoriteSeries.imageData.mediumQualityUrl
                )
            )
        } catch (throwable: Throwable) {
            return Result.failure(throwable)
        }
        return Result.success(Unit)
    }
}
