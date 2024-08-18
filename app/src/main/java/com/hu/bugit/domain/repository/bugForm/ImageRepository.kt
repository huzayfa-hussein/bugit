package com.hu.bugit.domain.repository.bugForm

import com.hu.bugit.domain.models.base.DomainBaseModel

interface ImageRepository {

    suspend fun uploadImage(imagePath: String): DomainBaseModel<String>
}