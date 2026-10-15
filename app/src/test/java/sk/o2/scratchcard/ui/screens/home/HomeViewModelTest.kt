package sk.o2.scratchcard.ui.screens.home

import io.mockk.coEvery
import io.mockk.coVerify
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
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.PreferencesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val preferencesRepository = mockk<PreferencesRepository>(relaxed = true)
    lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { preferencesRepository.getUserSettings() } returns flowOf(UserSettings())

        viewModel = HomeViewModel(
            repository = preferencesRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is initialized, settingsState emits repository settings`() {
        val uiState = viewModel.settingsState.value
        assert(uiState.code == null)
        assert(!uiState.isCodeActivated)
    }

    @Test
    fun `when resetSettings is called, repository resetSettings is invoked`() = runTest {
        viewModel.resetSettings()

        advanceUntilIdle()

        coVerify { preferencesRepository.resetSettings() }
    }

    @Test
    fun `when deleteCode is called, repository deleteCode is invoked`() = runTest {
        viewModel.deleteCode()

        advanceUntilIdle()

        coVerify { preferencesRepository.deleteCode() }
    }

}