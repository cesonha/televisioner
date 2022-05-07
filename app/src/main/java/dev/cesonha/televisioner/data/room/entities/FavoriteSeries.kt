package dev.cesonha.televisioner.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteSeries(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "seriesId") val seriesId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "posterUrl") val posterUrl: String
)
