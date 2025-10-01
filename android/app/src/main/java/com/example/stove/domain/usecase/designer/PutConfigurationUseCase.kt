package com.example.stove.domain.usecase.designer

import com.example.stove.core.Resource
import com.example.stove.data.dto.AddonIdDto
import com.example.stove.data.dto.NewConfigurationDto
import com.example.stove.data.dto.OptionIdDto
import com.example.stove.domain.model.designer.Configuration
import com.example.stove.domain.repository.DesignerRepository
import javax.inject.Inject

class PutConfigurationUseCase @Inject constructor(
    private val designerRepository: DesignerRepository
) {
    suspend fun invoke(configuration: Configuration) : Resource<Unit> {
        try {
            designerRepository.putConfiguration(configuration.toData())
            return Resource.SUCCESS(Unit)
        } catch (e: Throwable) {
            return Resource.FAILURE(e)
        }
    }

    fun Configuration.toData() : NewConfigurationDto {
        if(typeId != null) {
            return NewConfigurationDto(
                typeId,
                draftName,
                optionIds.map { it -> OptionIdDto(it) },
                addonIds.map { it -> AddonIdDto(it) }
            )
        } else {
            throw(Throwable("Error with data: type_id = null"))
        }

    }
}