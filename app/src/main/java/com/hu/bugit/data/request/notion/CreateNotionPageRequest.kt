package com.hu.bugit.data.request.notion

import java.io.Serializable

data class CreateNotionPageRequest(
    val parent: Parent,
    val properties: Map<String, PropertyValue>,
    val children: List<Block>? = null
) : Serializable