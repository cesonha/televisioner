package dev.cesonha.televisioner.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.cesonha.televisioner.core.di.IoDispatcher
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.GetFavoriteSeriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteSeries: GetFavoriteSeriesUseCase,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) :
    ViewModel() {

    private val _state: MutableLiveData<FavoritesFragmentState<List<Series>>> =
        MutableLiveData(FavoritesFragmentState.Loading())
    val state: LiveData<FavoritesFragmentState<List<Series>>> = _state

    fun fetchFavoriteSeries() {
        viewModelScope.launch(defaultDispatcher) {

            val result = getFavoriteSeries.execute()
            if (result.isSuccess) {
                val favoriteSeries = result.getOrNull() as List<Series>
                if (favoriteSeries.isEmpty()) {
                    _state.postValue(FavoritesFragmentState.Empty())
                } else {
                    _state.postValue(FavoritesFragmentState.Success(favoriteSeries))
                }
            } else {
                _state.postValue(FavoritesFragmentState.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
            }
        }
    }

    sealed class FavoritesFragmentState<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Success<T>(data: T) : FavoritesFragmentState<T>(data)
        class Error<T>(message: String, data: T? = null) : FavoritesFragmentState<T>(data, message)
        class Loading<T> : FavoritesFragmentState<T>()
        class Empty<T> : FavoritesFragmentState<T>()
    }
}
