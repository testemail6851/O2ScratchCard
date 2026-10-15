package sk.o2.scratchcard.ui.screens.activate

import app.cash.turbine.test
import io.mockk.coEvery
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
import sk.o2.scratchcard.R
import sk.o2.scratchcard.domain.model.ActivationResult
import sk.o2.scratchcard.domain.model.UserSettings
import sk.o2.scratchcard.domain.repository.ApiRepository
import sk.o2.scratchcard.domain.repository.PreferencesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class ActivateViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val code = "12345"

    val preferencesRepository = mockk<PreferencesRepository>()
    val apiRepository = mockk<ApiRepository>()
    lateinit var repository: ActivateViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { preferencesRepository.getUserSettings() } returns flowOf(
            UserSettings(
                code = code,
                isCodeActivated = false
            )
        )

        repository = ActivateViewModel(
            preferencesRepository,
            apiRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is initialized, initial state is correct`() {
        val uiState = repository.uiState.value
        assert(!uiState.isActivating)
        assert(uiState.result == null)
    }

    @Test
    fun `when activating code successfully, result is Success`() = runTest {
        coEvery { apiRepository.activateCode(code) } returns ActivationResult.Success

        repository.activateCode(code)

        advanceUntilIdle()

        val uiState = repository.uiState.value
        assert(uiState.result is ActivationResult.Success)
        assert(!uiState.isActivating)
    }

    @Test
    fun `when activating code with no internet, result is NoInternet and snackbar effect is emitted`() =
        runTest {
            coEvery { apiRepository.activateCode(code) } returns ActivationResult.NoInternet

            repository.uiEffect.test {
                repository.activateCode(code)

                advanceUntilIdle()

                val emission = awaitItem()
                assert(emission is ActivateUiEffect.ShowSnackBar)
                assert((emission as ActivateUiEffect.ShowSnackBar).messageResId == R.string.no_network)
            }

            val uiState = repository.uiState.value
            assert(uiState.result is ActivationResult.NoInternet)
            assert(!uiState.isActivating)
        }

    @Test
    fun `when activating code fails with API error, result is ApiError`() = runTest {
        val errorMessage = "Generic error"
        coEvery { apiRepository.activateCode(code) } returns ActivationResult.ApiError(errorMessage)

        repository.activateCode(code)

        advanceUntilIdle()

        val uiState = repository.uiState.value
        assert(uiState.result is ActivationResult.ApiError)
        assert((uiState.result as ActivationResult.ApiError).message == errorMessage)
    }

    @Test
    fun `when activating code fails with invalid code, result is InvalidCode`() = runTest {
        val errorMessage = "Invalid code"
        coEvery { apiRepository.activateCode(code) } returns ActivationResult.InvalidCode(
            errorMessage
        )

        repository.activateCode(code)

        advanceUntilIdle()

        val uiState = repository.uiState.value
        assert(uiState.result is ActivationResult.InvalidCode)
        assert((uiState.result as ActivationResult.InvalidCode).message == errorMessage)
    }

    @Test
    fun `when dismissing error modal, result is reset to null`() = runTest {
        val errorMessage = "Generic error"
        coEvery { apiRepository.activateCode(code) } returns ActivationResult.ApiError(errorMessage)

        repository.activateCode(code)

        advanceUntilIdle()

        repository.dismissErrorModal()

        val uiState = repository.uiState.value
        assert(uiState.result == null)
    }

}