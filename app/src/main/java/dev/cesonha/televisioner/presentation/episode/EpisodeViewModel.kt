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
class EpisodeViewModel @Inject constructor(private val getEpisodeDetails: GetEpisodeDetailsUseCase) :
    ViewModel() {

    private val _state: MutableLiveData<EpisodeFragmentState<Episode>> =
        MutableLiveData(EpisodeFragmentState.Loading())
    val state: LiveData<EpisodeFragmentState<Episode>> = _state

    fun fetchEpisodeDetails(episodeId: Int) {
        _state.postValue(EpisodeFragmentState.Loading())

        viewModelScope.launch(Dispatchers.IO) {

            val result = getEpisodeDetails(episodeId)
            if (result.isSuccess) {
                val episode = result.getOrNull() as Episode
                episode.let {
                    it.summary =
                        HtmlCompat.fromHtml(it.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    _state.postValue(EpisodeFragmentState.Success(it))
                }
            } else {
                // use error msg
                _state.postValue(EpisodeFragmentState.Error(result.exceptionOrNull().toString()))
            }
        }
    }

    sealed class EpisodeFragmentState<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Success<T>(data: T) : EpisodeFragmentState<T>(data)
        class Error<T>(message: String, data: T? = null) : EpisodeFragmentState<T>(data, message)
        class Loading<T> : EpisodeFragmentState<T>()
    }
}
