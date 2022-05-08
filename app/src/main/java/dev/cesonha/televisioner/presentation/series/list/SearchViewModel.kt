package dev.cesonha.televisioner.presentation.series.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.core.di.IoDispatcher
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.SearchSeriesUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSeries: SearchSeriesUseCase,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var currentSearchJob: Job? = null

    private val _series = MutableLiveData<List<Series>>()
    val series: LiveData<List<Series>> = _series

    private val _state = MutableLiveData(SearchViewState.LOADING)
    val state: LiveData<SearchViewState> = _state

    fun fetchSeries(query: String) {
        currentSearchJob?.cancel()
        currentSearchJob = null

        _state.postValue(SearchViewState.LOADING)

        currentSearchJob = viewModelScope.launch(defaultDispatcher) {
            delay(300)
            val result = searchSeries(query)

            if (result.isSuccess) {
                val fetchedSeries = result.getOrNull() as List<Series>
                _series.postValue(fetchedSeries)
                _state.postValue(SearchViewState.SUCCESS)
            } else {
                _state.postValue(SearchViewState.ERROR)
            }
        }
    }

    enum class SearchViewState {
        LOADING,
        SUCCESS,
        ERROR
    }
}
