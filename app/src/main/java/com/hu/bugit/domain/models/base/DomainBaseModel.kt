package com.hu.bugit.domain.models.base

import java.io.Serializable

/**
 * A data class representing a base model for domain-related operations.
 * @param T The type of data associated with the model.
 * @property isSuccessful A flag indicating whether the operation was successful.
 * @property message An optional message providing additional information.
 * @property data The data associated with the model.
 */
data class DomainBaseModel<T>(
    val isSuccessful: Boolean,
    val message: String?,
    val data: T?
) : Serializable