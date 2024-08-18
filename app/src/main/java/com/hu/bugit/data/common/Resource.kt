package com.hu.bugit.data.common

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null) :
        Resource<T>(data = data)

    class Error<T>(message: String, error: String? = null) : Resource<T>(null, error, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}