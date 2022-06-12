package com.unava.dia.weatherforecast.di

import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.domain.useCases.GetCurrentWeatherUseCase
import com.unava.dia.weatherforecast.domain.useCases.GetFutureWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(api: ApiInterface) = GetCurrentWeatherUseCase(api)

    @Singleton
    @Provides
    fun provideGetFutureWeatherUseCase(api: ApiInterface) = GetFutureWeatherUseCase(api)
}