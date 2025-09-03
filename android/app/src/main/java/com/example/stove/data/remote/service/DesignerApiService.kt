package com.example.stove.data.remote.service

import com.example.stove.data.remote.dto.AddonDto
import com.example.stove.data.remote.dto.ComponentDto
import com.example.stove.data.remote.dto.OptionDto
import com.example.stove.data.remote.dto.TypeDto
import retrofit2.http.GET
import retrofit2.http.Path


interface DesignerApiService {
    @GET("stove-types")
    suspend fun getTypes() : List<TypeDto>

    @GET("stove-types/{typeId}/components")
    suspend fun getComponents(
        @Path("typeId") typeId: Int
    ) : List<ComponentDto>

    @GET("components/{componentId}/options")
    suspend fun getOptions(
        @Path("componentId") componentId: Int
    ) : List<OptionDto>

    @GET("addons")
    suspend fun getAddons() : List<AddonDto>
}