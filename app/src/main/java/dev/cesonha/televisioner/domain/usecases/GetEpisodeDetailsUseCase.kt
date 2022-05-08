package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.repositories.ISeriesRepository
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(private val repository: ISeriesRepository) {

    suspend operator fun invoke(episodeId: Int): Result<Episode> {
        return repository.getEpisodeDetails(episodeId)
    }
}
