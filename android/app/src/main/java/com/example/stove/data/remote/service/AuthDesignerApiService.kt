package com.example.stove.data.remote.service

import com.example.stove.data.dto.NewConfigurationDto
import com.example.stove.data.dto.NewConfigurationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthDesignerApiService {
    @POST("api/v1/configurations")
    suspend fun putConfiguration(@Body newConfigurationDto: NewConfigurationDto) : Response<NewConfigurationResponse>


}