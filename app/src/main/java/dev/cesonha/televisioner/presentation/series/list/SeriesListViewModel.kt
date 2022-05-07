package dev.cesonha.televisioner.presentation.series.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.GetSeriesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesListViewModel @Inject constructor(
    private val getSeriesList: GetSeriesListUseCase
) : ViewModel() {

    private val _series = MutableLiveData<List<Series>>()
    val series: LiveData<List<Series>> = _series

    fun fetchSeries(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val series = getSeriesList(page)
            val newSeries = mutableListOf<Series>()

            _series.value?.let {
                newSeries.addAll(it.toTypedArray())
            }

            series.let {
                newSeries.addAll(it)
            }

            _series.postValue(newSeries)
        }
    }
}
