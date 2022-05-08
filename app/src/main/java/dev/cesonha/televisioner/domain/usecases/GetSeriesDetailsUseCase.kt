package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(private val repository: ISeriesRepository) {

    suspend operator fun invoke(seriesId: Int): Result<Series> {
        return repository.getSeriesDetails(seriesId)
    }
}
