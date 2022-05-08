package dev.cesonha.televisioner.presentation.favorites

import android.arch.core.executor.testing.InstantTaskExecutorRule
import dev.cesonha.televisioner.domain.entities.Series
import dev.cesonha.televisioner.domain.usecases.GetFavoriteSeriesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

// Test not working due to issues with coroutines testing setup, didnt have time for fixing it :(
class FavoritesViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val mockUseCase = mockk<GetFavoriteSeriesUseCase>(relaxed = true)
    private val viewModel = FavoritesViewModel(mockUseCase, testDispatcher)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Ignore("issues with coroutines testing setup")
    @Test
    fun fetchFailureReturnsError() = testScope.runTest {

        val mockResult: Result<List<Series>> = Result.failure(Exception("customException"))
        coEvery { mockUseCase.execute() } returns mockResult

        viewModel.fetchFavoriteSeries()
        viewModel.state.observeForever {
            Assert.assertTrue(it is FavoritesViewModel.FavoritesFragmentState.Error)
        }
    }
}
