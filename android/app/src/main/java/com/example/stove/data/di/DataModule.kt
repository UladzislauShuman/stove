package com.example.stove.data.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.stove.data.remote.service.AuthApiService
import com.example.stove.data.remote.service.DesignerApiService
import com.example.stove.data.remote.service.ProfileApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.security.GeneralSecurityException
import javax.inject.Qualifier
import javax.inject.Singleton

private const val baseUrl = "https://personal-lat-bottles-gaps.trycloudflare.com/"

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

    @Singleton
    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @PublicOkHttpClient
    fun getPublicOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @AuthOkHttpClient
    fun getAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @PublicRetrofit
    fun getPublicRetrofit(moshi: Moshi, @PublicOkHttpClient okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun getAuthRetrofit(moshi: Moshi, @AuthOkHttpClient okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun getEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKeyAlias = "shared_prefs_master_key"

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            masterKeyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        val masterKey = MasterKey.Builder(context, masterKeyAlias)
            .setKeyGenParameterSpec(keyGenParameterSpec)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    @Provides
    @Singleton
    fun getDesignerService(@PublicRetrofit retrofit: Retrofit) : DesignerApiService {
        return retrofit.create(DesignerApiService::class.java)
    }

    @Provides
    @Singleton
    fun getAuthService(@PublicRetrofit retrofit: Retrofit) : AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun getProfileService(@AuthRetrofit retrofit: Retrofit) : ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthRetrofit
}