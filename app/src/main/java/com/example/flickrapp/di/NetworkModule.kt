package com.example.flickrapp.di

import android.content.Context
import androidx.room.Room
import com.example.flickrapp.FlickrApp
import com.example.flickrapp.core.ApiService
import com.example.flickrapp.core.CONNECTION_TIMEOUT
import com.example.flickrapp.core.WEB_SERVICE_URL
import com.example.flickrapp.data.database.HistoryDao
import com.example.flickrapp.data.database.HistorySearchDatabase
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(WEB_SERVICE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): HistorySearchDatabase {
        return Room.databaseBuilder(
            appContext,
            HistorySearchDatabase::class.java,
            "FlickrAppDatabase"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideHistoryDao(appDatabase: HistorySearchDatabase): HistoryDao {
        return appDatabase.dao
    }

    private val TAG = NetworkModule::class.simpleName
}
