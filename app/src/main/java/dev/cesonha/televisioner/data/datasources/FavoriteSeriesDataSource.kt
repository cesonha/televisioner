package dev.cesonha.televisioner.data.datasources

import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import dev.cesonha.televisioner.data.room.entities.FavoriteSeries
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class FavoriteSeriesDataSource @Inject constructor(private val dao: FavoriteSeriesDao) {

    fun getFavoriteSeries(): List<Series> {
        return dao.getFavoriteSeries().map { Series(it.seriesId, it.name, it.posterUrl) }
    }

    fun isFavoriteSeries(seriesId: Int): Boolean {
        return dao.getSeriesFromFavorites(seriesId) != null
    }

    fun addFavoriteSeries(favoriteSeries: Series) {
        dao.addFavoriteSeries(
            FavoriteSeries(
                favoriteSeries.id, favoriteSeries.id,
                favoriteSeries.name, favoriteSeries.imageData.originalUrl
            )
        )
    }

    fun removeFavoriteSeries(favoriteSeries: Series) {
        dao.removeFavoriteSeries(
            FavoriteSeries(
                favoriteSeries.id, favoriteSeries.id,
                favoriteSeries.name, favoriteSeries.imageData.mediumQualityUrl
            )
        )
    }
}
