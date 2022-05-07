package dev.cesonha.televisioner.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.cesonha.televisioner.core.Constants.Companion.APP_DATABASE
import dev.cesonha.televisioner.data.api.TvMazeService
import dev.cesonha.televisioner.data.room.AppDatabase
import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, APP_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesFavoriteSeriesDao(database: AppDatabase): FavoriteSeriesDao {
        return database.favoriteSeries()
    }

    @Provides
    fun providesTvMazeService(retrofit: Retrofit): TvMazeService {
        return retrofit.create(TvMazeService::class.java)
    }
}
