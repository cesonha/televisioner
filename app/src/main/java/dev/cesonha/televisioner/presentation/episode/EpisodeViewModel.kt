package dev.cesonha.televisioner.presentation.episode

import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.domain.entities.Episode
import dev.cesonha.televisioner.domain.usecases.GetEpisodeDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val getEpisodeDetails: GetEpisodeDetailsUseCase) : ViewModel() {

    private val _state = MutableLiveData(EpisodeFragmentState.LOADING)
    val state: LiveData<EpisodeFragmentState> = _state

    private val _episode = MutableLiveData<Episode>()
    val episode: LiveData<Episode> = _episode

    fun fetchEpisodeDetails(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val episode = getEpisodeDetails(episodeId)
            episode.let {
                it.summary = HtmlCompat.fromHtml(it.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                _episode.postValue(it)
                _state.postValue(EpisodeFragmentState.SUCCESS)
            }
        }
    }

    enum class EpisodeFragmentState {
        LOADING,
        SUCCESS,
        ERROR
    }
}
