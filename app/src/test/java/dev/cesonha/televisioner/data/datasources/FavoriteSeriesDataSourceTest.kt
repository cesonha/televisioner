package dev.cesonha.televisioner.data.datasources

import dev.cesonha.televisioner.data.room.dao.FavoriteSeriesDao
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class FavoriteSeriesDataSourceTest {

    private val mockDao = mockk<FavoriteSeriesDao>(relaxed = true)
    private val dataSource = FavoriteSeriesDataSource(mockDao)

    @Test
    fun crashWhileFetchingReturnsError() {

        every { mockDao.getFavoriteSeries() } throws Exception("Test Exception")

        val result = dataSource.getFavoriteSeries()
        Assert.assertTrue(result.isFailure && result.exceptionOrNull()?.message == "Test Exception")
    }
}
