package com.hu.bugit.extensions

import com.hu.bugit.data.common.Resource


suspend fun <T> Resource<T>.onSuccess(
    executable: suspend (T) -> Unit
): Resource<T> = apply {
    if (this is Resource.Success<T> && data != null) {
        executable(data)
    }
}

suspend fun <T> Resource<T>.onNullableSuccess(
    executable: suspend (T?) -> Unit
): Resource<T> = apply {
    if (this is Resource.Success<T>) {
        executable(data)
    }
}


suspend fun <T> Resource<T>.onLoading(
    executable: suspend (T?) -> Unit
): Resource<T> = apply {
    if (this is Resource.Loading<T>) {
        executable(data)
    }
}

suspend fun <T> Resource<T>.onError(
    executable: suspend (message: String?, error: String?) -> Unit
): Resource<T> = apply {
    if (this is Resource.Error<T>) {
        executable(message, error)
    }
}
