package dev.cesonha.televisioner.presentation.series.details

import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.AddFavoriteSeriesUseCase
import dev.cesonha.televisioner.domain.usecases.GetSeriesDetailsUseCase
import dev.cesonha.televisioner.domain.usecases.IsFavoriteSeriesUseCase
import dev.cesonha.televisioner.domain.usecases.RemoveFavoriteSeriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val getSeriesDetails: GetSeriesDetailsUseCase,
    private val addFavoriteSeries: AddFavoriteSeriesUseCase,
    private val removeFavoriteSeries: RemoveFavoriteSeriesUseCase,
    private val isFavoriteSeries: IsFavoriteSeriesUseCase
) : ViewModel() {

    private val _series = MutableLiveData<Series>()
    val series: LiveData<Series> = _series

    private val _state = MutableLiveData(SeriesDetailsFragmentState.LOADING)
    val state: LiveData<SeriesDetailsFragmentState> = _state

    private val _favorited = MutableLiveData(false)
    val favorited: LiveData<Boolean> = _favorited

    fun fetchSeriesDetails(seriesId: Int) {

        checkIfSeriesIsFavorite(seriesId)

        viewModelScope.launch(Dispatchers.IO) {
            val seriesWithDetails = getSeriesDetails(seriesId)

            seriesWithDetails.let {
                it.summary = HtmlCompat.fromHtml(it.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                _series.postValue(seriesWithDetails)
                _state.postValue(SeriesDetailsFragmentState.SUCCESS)
            }
        }
    }

    fun onFavoriteButtonTap(series: Series) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_favorited.value == true) {
                removeFavoriteSeries(series)
                _favorited.postValue(false)
            } else {
                addFavoriteSeries(series)
                _favorited.postValue(true)
            }
        }
    }

    fun checkIfSeriesIsFavorite(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavoriteSeries(seriesId)) {
                _favorited.postValue(true)
            } else {
                _favorited.postValue(false)
            }
        }
    }

    enum class SeriesDetailsFragmentState {
        LOADING,
        SUCCESS,
        ERROR
    }
}
