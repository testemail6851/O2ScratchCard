package sk.o2.scratchcard.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import sk.o2.scratchcard.data.local.Constants
import sk.o2.scratchcard.data.remote.O2Api
import sk.o2.scratchcard.domain.model.ActivationResult
import sk.o2.scratchcard.domain.model.NetworkMonitor
import sk.o2.scratchcard.domain.repository.ApiRepository
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class ApiRepositoryImpl @Inject constructor(
    private val api: O2Api,
    private val preferencesRepository: PreferencesRepository,
    private val networkMonitor: NetworkMonitor
) : ApiRepository {

    override suspend fun activateCode(code: String): ActivationResult =
        withContext(NonCancellable) {
            if (!networkMonitor.isNetworkAvailable()) {
                return@withContext ActivationResult.NoInternet
            }

            try {
                delay(3000L.milliseconds)
                /*if (Random.nextBoolean()) {
                    return@withContext ActivationResult.InvalidCode("Invalid code: $code")
                }*/

                val response = retry(3) {
                    api.getVersion(code)
                }

                val androidValue = response.android?.toIntOrNull()
                    ?: return@withContext ActivationResult.ApiError("Invalid API response")
                val isSuccess = androidValue > Constants.API_VERSION_SUCCESS

                if (isSuccess) {
                    preferencesRepository.updateIsCodeActivated(true)
                    return@withContext ActivationResult.Success
                }

                return@withContext ActivationResult.InvalidCode("Invalid code: $code")
            } catch (e: Exception) {
                return@withContext ActivationResult.ApiError(e.message)
            }
        }
}

private suspend fun <T> retry(
    times: Int = 3,
    initialDelay: Long = 1000,
    maxDelay: Long = 4000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        delay(currentDelay.milliseconds)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block()
}
