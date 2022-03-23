package com.unava.dia.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.unava.dia.weatherforecast.data.api.ApiInterface
import com.unava.dia.weatherforecast.data.api.AppDatabase
import com.unava.dia.weatherforecast.data.api.RetrofitFactory
import com.unava.dia.weatherforecast.data.dao.CurrentWeatherResponseDao
import com.unava.dia.weatherforecast.data.dao.FutureWeatherResponseDao
import com.unava.dia.weatherforecast.data.repository.IWeatherRepository
import com.unava.dia.weatherforecast.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "myDB"
    ).allowMainThreadQueries()
        .build()

    @Provides
    fun provideCurrentWeatherDao(db: AppDatabase) = db.currentWeatherDao()

    @Provides
    fun provideFutureWeatherDao(db: AppDatabase) = db.futureWeatherDao()

    @Provides
    fun provideRepository(
        currentWeatherDao: CurrentWeatherResponseDao,
        futureWeatherDao: FutureWeatherResponseDao,
    ): IWeatherRepository =
        WeatherRepository(currentWeatherDao, futureWeatherDao)

    @Provides
    fun provideApi(): ApiInterface = RetrofitFactory.api()
}