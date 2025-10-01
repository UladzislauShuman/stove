package com.example.stove.data.repository

import android.util.Log
import com.example.stove.core.Resource
import com.example.stove.data.dto.AddonDto
import com.example.stove.data.dto.ComponentDto
import com.example.stove.data.dto.NewConfigurationDto
import com.example.stove.data.dto.OptionDto
import com.example.stove.data.dto.TypeDto
import com.example.stove.data.remote.service.DesignerApiService
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DesignerRepositoryImpl @Inject constructor(
    private val apiService: DesignerApiService
) : DesignerRepository {

    override suspend fun putConfiguration(configuration: NewConfigurationDto): Resource<Unit> {
        return try {
            val response = apiService.putConfiguration(configuration)
            if(response.isSuccessful) {
                Resource.SUCCESS(Unit)
            } else {
                val errorBody = response.errorBody()
                Resource.FAILURE(Throwable(errorBody.toString()))
            }
        } catch(e: HttpException) {
            Resource.FAILURE(Throwable(e.message))
        } catch(e: IOException) {
            Resource.FAILURE(Throwable("Network error. Check your connection."))
        }
    }

    override suspend fun getTypes(): Resource<List<Type>> {
        return try {
            val remoteTypes = apiService.getTypes()
            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }

    override suspend fun getComponents(typeId: Int): Resource<List<Component>> {
        return try {
            val remoteComponents = apiService.getComponents(typeId)
            Resource.SUCCESS(remoteComponents.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }
    override suspend fun getOptions(componentId: Int): Resource<List<Option>> {
        return try {
            val remoteOptions = apiService.getOptions(componentId)
            Resource.SUCCESS(remoteOptions.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }
    override suspend fun getAddons(): Resource<List<Addon>> {
        return try {
            val remoteTypes = apiService.getAddons()
            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
        } catch(e: HttpException) {
            Log.e("DesignerRepository",e.message ?: "Unknown Error")
            Resource.FAILURE(e)
        }
    }

    private fun TypeDto.toDomain() = Type(id, name, description, basePrice ?: 0, imageUrl ?: "")
    private fun ComponentDto.toDomain() = Component(id, name, description, isRequired ?: false, allowMultipleChoices ?: false, componentOptions ?: false)

    private fun OptionDto.toDomain() = Option(id, name, priceModifier ?: 1, imageUrl ?: "", isDefault ?: true)

    private fun AddonDto.toDomain() = Addon(id, name, description, price ?: 0)
}