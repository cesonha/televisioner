package dev.cesonha.televisioner.domain.usecases

import dev.cesonha.televisioner.data.repositories.SeriesRepository
import dev.cesonha.televisioner.domain.entities.Episode
import javax.inject.Inject

class GetEpisodeDetailsUseCase @Inject constructor(private val repository: SeriesRepository) {

    suspend operator fun invoke(episodeId: Int): Episode {
        return repository.getEpisodeDetails(episodeId).body()!!
    }
}
