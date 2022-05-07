package dev.cesonha.televisioner.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.GetFavoriteSeriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteSeries: GetFavoriteSeriesUseCase
) :
    ViewModel() {

    private val _series = MutableLiveData<List<Series>>()
    val series: LiveData<List<Series>> = _series

    private val _state = MutableLiveData(FavoritesFragmentState.LOADING)
    val state: LiveData<FavoritesFragmentState> = _state

    fun fetchFavoriteSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteSeries = getFavoriteSeries()
            if (favoriteSeries.isEmpty()) {
                _state.postValue(FavoritesFragmentState.EMPTY)
            } else {
                _series.postValue(favoriteSeries)
                _state.postValue(FavoritesFragmentState.SUCCESS)
            }
        }
    }

    enum class FavoritesFragmentState {
        LOADING,
        SUCCESS,
        EMPTY,
        ERROR
    }
}
