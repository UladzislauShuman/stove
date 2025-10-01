package com.example.stove.data.remote.service

import com.example.stove.data.dto.AddonDto
import com.example.stove.data.dto.ComponentDto
import com.example.stove.data.dto.NewConfigurationDto
import com.example.stove.data.dto.NewConfigurationResponse
import com.example.stove.data.dto.OptionDto
import com.example.stove.data.dto.TypeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface DesignerApiService {
    @GET("constructor-data/stove-types")
    suspend fun getTypes() : List<TypeDto>

    @GET("constructor-data/stove-types/{typeId}/components")
    suspend fun getComponents(
        @Path("typeId") typeId: Int
    ) : List<ComponentDto>

    @GET("constructor-data/components/{componentId}/options")
    suspend fun getOptions(
        @Path("componentId") componentId: Int
    ) : List<OptionDto>

    @GET("constructor-data/addons")
    suspend fun getAddons() : List<AddonDto>

    @POST("api/v1/configurations")
    suspend fun putConfiguration(@Body newConfigurationDto: NewConfigurationDto) : Response<NewConfigurationResponse>
}