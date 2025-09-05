package com.example.stove.data.di

import com.example.stove.data.remote.service.DesignerApiService
import com.example.stove.domain.repository.DesignerRepository
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
    fun getMoshi() : Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun getRetrofit(moshi: Moshi) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://orders-chamber-harley-furthermore.trycloudflare.com/constructor-data/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun getDesignerService(retrofit: Retrofit) : DesignerApiService {
        return retrofit.create(DesignerApiService::class.java)
    }
}