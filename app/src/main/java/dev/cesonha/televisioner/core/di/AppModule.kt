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
import dev.cesonha.televisioner.data.datasources.FavoriteSeriesDataSource
import dev.cesonha.televisioner.data.datasources.SeriesDataSource
import dev.cesonha.televisioner.data.repositories.FavoriteSeriesRepository
import dev.cesonha.televisioner.data.repositories.SeriesRepository
import dev.cesonha.televisioner.data.room.AppDatabase
import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import dev.cesonha.televisioner.domain.repositories.IFavoriteSeriesRepository
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesISeriesRepository(datasource: SeriesDataSource): ISeriesRepository {
        return SeriesRepository(datasource)
    }

    @Provides
    fun providesIFavoriteSeriesRepository(datasource: FavoriteSeriesDataSource): IFavoriteSeriesRepository {
        return FavoriteSeriesRepository(datasource)
    }

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
