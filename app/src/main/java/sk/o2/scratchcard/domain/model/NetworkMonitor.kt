package sk.o2.scratchcard.domain.model

interface NetworkMonitor {
    suspend fun isNetworkAvailable(): Boolean
}