package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.SeriesRepository
import dev.cesonha.televisioner.domain.entities.Series
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(private val repository: SeriesRepository) {

    suspend operator fun invoke(seriesId: Int): Series {
        return repository.getSeriesDetails(seriesId).body()!!
    }
}
