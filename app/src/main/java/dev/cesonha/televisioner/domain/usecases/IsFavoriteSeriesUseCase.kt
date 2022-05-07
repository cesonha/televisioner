package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.FavoriteSeriesRepository
import javax.inject.Inject

class IsFavoriteSeriesUseCase @Inject constructor(private val repository: FavoriteSeriesRepository) {

    suspend operator fun invoke(seriesId: Int): Result<Boolean> {
        return repository.isFavoriteSeries(seriesId)
    }
}
