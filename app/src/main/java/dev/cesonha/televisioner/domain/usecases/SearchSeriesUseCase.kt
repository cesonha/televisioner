package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import javax.inject.Inject

class SearchSeriesUseCase @Inject constructor(private val repository: ISeriesRepository) {

    suspend operator fun invoke(query: String): Result<List<Series>> {
        return repository.searchSeries(query)
    }
}
