package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.FavoriteSeriesRepository
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class RemoveFavoriteSeriesUseCase @Inject constructor(private val repository: FavoriteSeriesRepository) {

    suspend operator fun invoke(series: Series) {
        repository.removeFavoriteSeries(series)
    }
}
