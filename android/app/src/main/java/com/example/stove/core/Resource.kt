package com.example.stove.core

sealed interface Resource<out T> {
    data object LOADING : Resource<Nothing>
    data class FAILURE(val message: String) : Resource<Nothing>
    data class SUCCESS<out T>(val result: T) : Resource<T>
}