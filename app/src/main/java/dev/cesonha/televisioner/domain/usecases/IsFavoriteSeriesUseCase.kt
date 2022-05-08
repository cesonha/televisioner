package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.repositories.IFavoriteSeriesRepository
import javax.inject.Inject

class IsFavoriteSeriesUseCase @Inject constructor(private val repository: IFavoriteSeriesRepository) {

    suspend operator fun invoke(seriesId: Int): Result<Boolean> {
        return repository.isFavoriteSeries(seriesId)
    }
}
