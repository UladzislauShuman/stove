package com.example.stove.domain.usecase

import com.example.stove.core.Resource
import com.example.stove.domain.model.designer.Type
import com.example.stove.domain.repository.DesignerRepository

class GetTypesUseCase (
    private val repository: DesignerRepository
) {
    suspend fun invoke() : Resource<List<Type>> {
        return repository.getTypes()
    }
}