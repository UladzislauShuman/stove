package com.example.stove.domain.repository

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Addon
import com.example.stove.domain.model.designer.Component
import com.example.stove.domain.model.designer.Option
import com.example.stove.domain.model.designer.Type

interface DesignerRepository {
    suspend fun getTypes() : Resource<List<Type>>

    suspend fun getComponents() : Resource<List<Component>>

    suspend fun getOptions() : Resource<List<Option>>

    suspend fun getAddons() : Resource<List<Addon>>
}