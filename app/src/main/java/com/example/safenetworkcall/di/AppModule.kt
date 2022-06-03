package com.example.safenetworkcall.di

import com.example.safenetworkcall.data.remote.network.CallApi
import com.example.safenetworkcall.data.remote.repository.Repository
import com.example.safenetworkcall.data.remote.repository.Repository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    companion object {
        private const val BASE_URL = "https://aquawaterapp.herokuapp.com"
    }

    @Singleton
    @Provides
    fun provideApi(): CallApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CallApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api : CallApi): Repository {
        return Repository_Impl(api)
    }

    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(api : CallApi): Repository_Impl = Repository_Impl(api)
}

