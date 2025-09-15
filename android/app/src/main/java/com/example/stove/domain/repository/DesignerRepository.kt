package com.example.stove.domain.repository

import com.example.stove.core.Resource
import com.example.stove.domain.dto.designer.Addon
import com.example.stove.domain.dto.designer.Component
import com.example.stove.domain.dto.designer.Option
import com.example.stove.domain.dto.designer.Type

interface DesignerRepository {
    suspend fun getTypes() : Resource<List<Type>>

    suspend fun getComponents(typeId: Int) : Resource<List<Component>>

    suspend fun getOptions(componentId: Int) : Resource<List<Option>>

    suspend fun getAddons() : Resource<List<Addon>>
}