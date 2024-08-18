package com.hu.bugit.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hu.bugit.data.common.Resource
import com.hu.bugit.domain.models.base.DomainBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Response


suspend fun <T> ViewModel.handleUseCase(
    flow: Flow<Resource<DomainBaseModel<T>>>,
    onEach: suspend (resource: Resource<DomainBaseModel<T>>) -> Unit
): Job? {
    return try {
        flow.flowOn(Dispatchers.IO).cancellable().onEach {
            onEach(it)
        }.launchIn(viewModelScope)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T, K> Response<T>.toDomainModel(dataMapper: (T?) -> K?): DomainBaseModel<K> {
    return DomainBaseModel(
        isSuccessful = isSuccessful,
        message = message() ?: "",
        data = dataMapper(body())
    )
}