package sk.o2.scratchcard.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.o2.scratchcard.data.repository.ApiRepositoryImpl
import sk.o2.scratchcard.data.repository.NetworkMonitorImpl
import sk.o2.scratchcard.domain.model.NetworkMonitor
import sk.o2.scratchcard.domain.repository.ApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    @Singleton
    abstract fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl
    ): ApiRepository

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        networkMonitorImpl: NetworkMonitorImpl
    ): NetworkMonitor
}
