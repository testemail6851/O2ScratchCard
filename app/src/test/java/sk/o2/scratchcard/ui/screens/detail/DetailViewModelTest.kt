package sk.o2.scratchcard.ui.screens.detail

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import sk.o2.scratchcard.data.local.Constants
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.PreferencesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val preferencesRepository = mockk<PreferencesRepository>(relaxed = true)
    lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { preferencesRepository.getUserSettings() } returns flowOf(UserSettings())

        viewModel = DetailViewModel(preferencesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when view model is initialized, initial state is correct`() {
        val uiState = viewModel.uiState.value
        assert(!uiState.isScratching)
        assert(uiState.progress == 0F)
        assert(uiState.elapsedTimeSeconds == 0F)
    }

    @Test
    fun `when scratchCard is called, isScratching is true`() = runTest {
        viewModel.scratchCard()

        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assert(!uiState.isScratching)
        assert(uiState.progress == 1F)
        assert(uiState.elapsedTimeSeconds == Constants.SCRATCH_DURATION_MS / 1000F)

        coVerify { preferencesRepository.saveCode(any()) }
    }
}