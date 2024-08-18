package com.hu.bugit.domain.models.bugForm

import java.io.Serializable

/**
 * Represents a Notion page with its URL.
 * @property notionUrl The URL of the Notion page.
 * @property publicNotionUrl The public URL of the Notion page.
 */
data class NotionPageModel(
    val notionUrl: String,
    val publicNotionUrl: String
) : Serializable
