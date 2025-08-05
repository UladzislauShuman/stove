package com.example.stove.data.di

import com.example.stove.data.remote.service.DesignerApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun getRetrofit() : Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/constructor-data")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun getDesignerService(retrofit: Retrofit) : DesignerApiService {
        return retrofit.create(DesignerApiService::class.java)
    }
}