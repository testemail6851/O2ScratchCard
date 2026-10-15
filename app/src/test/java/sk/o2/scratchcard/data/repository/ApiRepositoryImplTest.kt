package sk.o2.scratchcard.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import sk.o2.scratchcard.data.remote.O2Api
import sk.o2.scratchcard.data.remote.VersionDto
import sk.o2.scratchcard.domain.model.ActivationResult
import sk.o2.scratchcard.domain.model.NetworkMonitor
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class ApiRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    val code = "12345"

    val api = mockk<O2Api>()
    val preferencesRepository = mockk<PreferencesRepository>(relaxed = true)
    val networkMonitor = mockk<NetworkMonitor>()

    lateinit var repository: ApiRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = ApiRepositoryImpl(
            api = api,
            preferencesRepository = preferencesRepository,
            networkMonitor = networkMonitor
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when network is not available, returns NoInternet`() = runTest {
        coEvery { networkMonitor.isNetworkAvailable() } returns false

        val result = repository.activateCode(code)

        advanceTimeBy(3000L.milliseconds)

        assert(result == ActivationResult.NoInternet)
    }

    @Test
    fun `when api returns valid version (less than 277028), updates preferences and returns Success`() =
        runTest {
            coEvery { networkMonitor.isNetworkAvailable() } returns true
            coEvery { api.getVersion(code) } returns VersionDto(android = "277029")

            val result = repository.activateCode(code)

            advanceUntilIdle()

            assert(result is ActivationResult.Success)

            coVerify { preferencesRepository.updateIsCodeActivated(true) }
        }

    @Test
    fun `when api returns low version, returns InvalidCode`() = runTest {
        coEvery { networkMonitor.isNetworkAvailable() } returns true
        coEvery { api.getVersion(code) } returns VersionDto(android = "277027")

        val result = repository.activateCode(code)

        advanceUntilIdle()

        assert(result is ActivationResult.InvalidCode)
    }

    @Test
    fun `when api throws exception, returns ApiError`() = runTest {
        coEvery { networkMonitor.isNetworkAvailable() } returns true
        coEvery { api.getVersion(code) } throws Exception("Generic error")

        val result = repository.activateCode(code)

        advanceUntilIdle()

        assert(result is ActivationResult.ApiError)
    }

}