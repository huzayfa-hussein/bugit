package com.hu.bugit.domain.usecase.base

import com.hu.bugit.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.io.IOException

abstract class UseCase<T> {

    open fun tagName(): String = "name the useCase to specify the error"

    fun executeFlow(invoke: suspend (flowCollector: FlowCollector<Resource<T>>) -> Unit): Flow<Resource<T>> =
        flow {
            try {
                invoke(this)
            } catch (e: IOException) {
                emit(Resource.Error<T>(e.localizedMessage ?: "IO Exception IN ${tagName()}"))
            } catch (e: Exception) {
                emit(Resource.Error<T>(e.localizedMessage ?: "Exception IN ${tagName()}"))
            }
        }

}