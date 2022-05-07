package dev.cesonha.televisioner.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import dev.cesonha.televisioner.data.room.entities.FavoriteSeries

@Database(entities = [FavoriteSeries::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteSeries(): FavoriteSeriesDao
}
