package com.hu.bugit.data.remote.handler

import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.extensions.toDomainModel
import retrofit2.HttpException
import retrofit2.Response

/**
 * Represents a handler for making API calls.
 */
class ApiHandler {

    /**
     * Handles the API call and returns the result.
     * @param call The suspend function representing the API call.
     * @param dataMapper The function to map the API response to a domain model.
     * @return The result of the API call.
     */
    suspend fun <T, K> handleApiCall(
        call: suspend () -> Response<T>,
        dataMapper: (T?) -> K?
    ): DomainBaseModel<K> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val model = response.toDomainModel(dataMapper)
                DomainBaseModel(
                    isSuccessful = true,
                    message = model.message,
                    data = model.data
                )
            } else {
                DomainBaseModel(
                    isSuccessful = false,
                    message = "Failed to make the api call",
                    data = null
                )
            }
        } catch (ex: HttpException) {
            DomainBaseModel(isSuccessful = false, message = ex.message, data = null)
        } catch (exception: Exception) {
            DomainBaseModel(
                isSuccessful = false,
                message = exception.message,
                data = null
            )
        }
    }
}