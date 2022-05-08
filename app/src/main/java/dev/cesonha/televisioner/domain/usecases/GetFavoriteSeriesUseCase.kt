package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.IFavoriteSeriesRepository
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(private val repository: IFavoriteSeriesRepository) {

    suspend fun execute(): Result<List<Series>> {
        return repository.getFavoriteSeries()
    }
}
