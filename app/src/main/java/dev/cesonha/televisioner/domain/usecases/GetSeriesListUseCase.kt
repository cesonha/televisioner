package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import javax.inject.Inject

class GetSeriesListUseCase @Inject constructor(private val repository: ISeriesRepository) {

    suspend operator fun invoke(page: Int): Result<List<Series>> {
        return repository.getSeriesList(page)
    }
}
