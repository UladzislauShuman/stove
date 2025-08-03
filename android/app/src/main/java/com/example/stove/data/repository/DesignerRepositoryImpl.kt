package com.example.stove.data.repository

import com.example.stove.core.Resource
import com.example.stove.data.remote.dto.AddonDto
import com.example.stove.data.remote.dto.ComponentDto
import com.example.stove.data.remote.dto.OptionDto
import com.example.stove.data.remote.dto.TypeDto
import com.example.stove.data.remote.service.DesignerApiService
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository
import retrofit2.HttpException

class DesignerRepositoryImpl(
    private val apiService: DesignerApiService
) : DesignerRepository {

    override suspend fun getTypes(): Resource<List<Type>> {
        return try {
            val remoteTypes = apiService.getTypes()
            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
        } catch(e: HttpException) {
            Resource.FAILURE("Http error: ${e.message()}")
        }
    }

    override suspend fun getComponents(): Resource<List<Component>> {
//        return try {
//            val remoteComponents = apiService.getComponents()
//            Resource.SUCCESS(remoteComponents.map {it.toDomain()})
//        } catch(e: HttpException) {
//            Resource.FAILURE("Http error: ${e.message()}")
//        }
        TODO()
    }
    override suspend fun getOptions(): Resource<List<Option>> {
//        return try {
//            val remoteOptions = apiService.getOptions()
//            Resource.SUCCESS(remoteOptions.map {it.toDomain()})
//        } catch(e: HttpException) {
//            Resource.FAILURE("Http error: ${e.message()}")
//        }
        TODO()
    }
    override suspend fun getAddons(): Resource<List<Addon>> {
//        return try {
//            val remoteTypes = apiService.getTypes()
//            Resource.SUCCESS(remoteTypes.map {it.toDomain()})
//        } catch(e: HttpException) {
//            Resource.FAILURE("Http error: ${e.message()}")
//        }
        TODO()
    }

    private fun TypeDto.toDomain() = Type(id, name, description, basePrice, imageUrl)
    private fun ComponentDto.toDomain() = Component(id, name, description, isRequired, allowMultipleChoices, componentOptions)
    private fun OptionDto.toDomain() = Option(id, name, priceModifier, imageUrl, isDefault)
    private fun AddonDto.toDomain() = Addon(id, name, description, price)
}