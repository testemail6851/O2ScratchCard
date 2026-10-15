package sk.o2.scratchcard.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sk.o2.scratchcard.data.remote.O2Api
import sk.o2.scratchcard.di.ApplicationScope
import sk.o2.scratchcard.domain.model.NetworkMonitor
import sk.o2.scratchcard.domain.repository.ApiRepository
import sk.o2.scratchcard.domain.repository.PreferencesRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class NetworkMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {

    override suspend fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}