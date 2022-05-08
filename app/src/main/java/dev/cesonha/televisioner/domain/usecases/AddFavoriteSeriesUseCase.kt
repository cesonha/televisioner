package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.IFavoriteSeriesRepository
import javax.inject.Inject

class AddFavoriteSeriesUseCase @Inject constructor(private val repository: IFavoriteSeriesRepository) {

    suspend operator fun invoke(series: Series): Result<Unit> {
        return repository.addFavoriteSeries(series)
    }
}
