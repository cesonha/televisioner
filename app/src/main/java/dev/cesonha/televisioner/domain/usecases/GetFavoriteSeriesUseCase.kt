package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.FavoriteSeriesRepository
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(private val repository: FavoriteSeriesRepository) {

    suspend operator fun invoke(): Result<List<Series>> {
        return repository.getFavoriteSeries()
    }
}
