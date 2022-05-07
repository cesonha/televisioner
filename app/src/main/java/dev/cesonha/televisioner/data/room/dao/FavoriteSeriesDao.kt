package dev.cesonha.televisioner.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.cesonha.televisioner.data.room.entities.FavoriteSeries

@Dao
interface FavoriteSeriesDao {

    @Query("SELECT * FROM favoriteSeries ORDER BY name")
    fun getFavoriteSeries(): List<FavoriteSeries>

    @Query("SELECT * FROM favoriteSeries WHERE seriesId = :seriesId")
    fun getSeriesFromFavorites(seriesId: Int): FavoriteSeries?

    @Insert
    fun addFavoriteSeries(vararg series: FavoriteSeries)

    @Delete
    fun removeFavoriteSeries(series: FavoriteSeries)
}
