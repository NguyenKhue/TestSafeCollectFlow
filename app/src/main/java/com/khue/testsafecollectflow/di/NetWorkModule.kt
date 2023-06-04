package com.khue.testsafecollectflow.di

import com.khue.testsafecollectflow.NetworkConnectivityService
import com.khue.testsafecollectflow.NetworkConnectivityServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetWorkModule {

    @Binds
    abstract fun provideNetwork(impl: NetworkConnectivityServiceImpl): NetworkConnectivityService

}