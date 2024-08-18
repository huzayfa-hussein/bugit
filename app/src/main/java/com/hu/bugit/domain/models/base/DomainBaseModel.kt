package com.hu.bugit.domain.models.base

import java.io.Serializable

data class DomainBaseModel<T>(
    val isSuccessful: Boolean,
    val message: String?,
    val data: T?
) : Serializable