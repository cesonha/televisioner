package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.SeriesRepository
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class GetSeriesListUseCase @Inject constructor(private val repository: SeriesRepository) {

    suspend operator fun invoke(page: Int): Result<List<Series>> {
        return repository.getSeriesList(page)
    }
}
