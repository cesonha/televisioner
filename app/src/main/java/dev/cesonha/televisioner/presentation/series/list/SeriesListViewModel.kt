package dev.cesonha.televisioner.presentation.series.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.core.di.IoDispatcher
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.GetSeriesListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val getSeriesList: GetSeriesListUseCase,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _series = MutableLiveData<List<Series>>()
    val series: LiveData<List<Series>> = _series

    private val _state = MutableLiveData(SeriesListFragmentState.LOADING)
    val state: LiveData<SeriesListFragmentState> = _state

    fun fetchSeries(page: Int) {
        viewModelScope.launch(defaultDispatcher) {
            val result = getSeriesList(page)

            if (result.isSuccess) {
                val fetchedSeries = result.getOrNull() as List<Series>
                val newSeries = mutableListOf<Series>()
                _series.value?.let {
                    newSeries.addAll(it.toTypedArray())
                }

                fetchedSeries.let {
                    newSeries.addAll(it)
                }
                _series.postValue(newSeries)
                _state.postValue(SeriesListFragmentState.SUCCESS)
            } else {
                _state.postValue(SeriesListFragmentState.ERROR)
            }
        }
    }

    enum class SeriesListFragmentState {
        LOADING,
        SUCCESS,
        ERROR
    }
}
