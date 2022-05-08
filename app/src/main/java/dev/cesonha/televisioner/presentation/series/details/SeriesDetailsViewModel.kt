package dev.cesonha.televisioner.presentation.series.details

import androidx.annotation.VisibleForTesting
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

    private val _state: MutableLiveData<SeriesDetailsFragmentState<Series>> =
        MutableLiveData(SeriesDetailsFragmentState.Loading())
    val state: LiveData<SeriesDetailsFragmentState<Series>> = _state

    private val _favorited = MutableLiveData(false)
    val favorited: LiveData<Boolean> = _favorited

    fun fetchSeriesDetails(seriesId: Int) {

        _state.postValue(SeriesDetailsFragmentState.Loading())
        checkIfSeriesIsFavorite(seriesId)
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSeriesDetails(seriesId)

            if (result.isSuccess) {
                val seriesWithDetails = result.getOrNull() as Series
                seriesWithDetails.let {
                    it.summary = it.summary ?: "no_summary_available"
                    it.summary?.run {
                        it.summary =
                            HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    }
                    _state.postValue(SeriesDetailsFragmentState.Success(seriesWithDetails))
                }
            } else {
                _state.postValue(SeriesDetailsFragmentState.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
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

    @VisibleForTesting
    fun checkIfSeriesIsFavorite(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavoriteSeries(seriesId).getOrNull() == true) {
                _favorited.postValue(true)
            } else {
                _favorited.postValue(false)
            }
        }
    }

    sealed class SeriesDetailsFragmentState<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Success<T>(data: T) : SeriesDetailsFragmentState<T>(data)
        class Error<T>(message: String, data: T? = null) :
            SeriesDetailsFragmentState<T>(data, message)

        class Loading<T> : SeriesDetailsFragmentState<T>()
    }
}
