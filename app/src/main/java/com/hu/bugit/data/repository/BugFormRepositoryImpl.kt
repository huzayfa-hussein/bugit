package com.hu.bugit.data.repository

import com.hu.bugit.data.dto.CreateNotionPageDto
import com.hu.bugit.data.persistence.BugDao
import com.hu.bugit.data.persistence.entity.BugData
import com.hu.bugit.data.remote.NotionApiService
import com.hu.bugit.data.remote.handler.ApiHandler
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.base.DomainBaseModel
import com.hu.bugit.domain.models.bugForm.NotionPageModel
import com.hu.bugit.domain.repository.bugForm.BugFormRepository
import com.hu.bugit.extensions.DATE_ISO_8601
import com.hu.bugit.extensions.convertToDateString
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.Date
import javax.inject.Inject

class BugFormRepositoryImpl @Inject constructor(
    private val notionApiService: NotionApiService,
    private val apiHandler: ApiHandler,
    private val bugDao: BugDao
) : BugFormRepository {

    override suspend fun createBug(
        title: String,
        description: String,
        imageUrl: String,
        platform: BugPlatform
    ): DomainBaseModel<NotionPageModel> {
        val mapper: (CreateNotionPageDto?) -> NotionPageModel = {
            NotionPageModel(
                notionUrl = it?.url ?: "",
                publicNotionUrl = it?.publicUrl ?: ""
            )
        }
        val jsonRequestBody = createNotionPageRequest(
            title = title,
            description = description,
            imageUrl = imageUrl,
            formattedDate = Date().convertToDateString(format = DATE_ISO_8601),
            databaseId = "9e07fc07be8848b184d8caa8935360f6"
        )
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonRequestBody
        )

        val response = apiHandler.handleApiCall(dataMapper = mapper,
            call = { notionApiService.createPage(requestBody) }
        )
        if (response.isSuccessful) {
            bugDao.insertNewBug(
                BugData(
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    platform = platform,
                    createdAt = Date().convertToDateString(),
                    url = response.data?.publicNotionUrl ?: ""
                )
            )
            return DomainBaseModel(
                isSuccessful = true,
                data = null,
                message = "Bug created successfully"
            )
        } else {
            return DomainBaseModel(isSuccessful = false, data = null, message = response.message)
        }


    }

    private fun createNotionPageRequest(
        title: String,
        description: String,
        imageUrl: String,
        formattedDate: String,
        databaseId: String
    ): String {
        val properties = JSONObject().apply {
            put("Name", JSONObject().apply {
                put("title", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", JSONObject().apply {
                            put("content", title)
                        })
                    })
                })
            })

            put("Description", JSONObject().apply {
                put("rich_text", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", JSONObject().apply {
                            put("content", description)
                        })
                    })
                })
            })
//            put("Summary", JSONObject().apply {
//                put("rich_text", JSONArray().apply {
//                    put(JSONObject().apply {
//                        put("text", JSONObject().apply {
//                            put("content", description)
//                        })
//                    })
//                })
//            })
            put("ImageURL", JSONObject().apply {
                put("url", imageUrl)
            })
            put("Date", JSONObject().apply {
                put("date", JSONObject().apply {
                    put("start", formattedDate)
                })
            })
        }

        return JSONObject().apply {
            put("parent", JSONObject().apply {
                put("database_id", "9e07fc07be8848b184d8caa8935360f6")
            })
            put("properties", properties)
        }.toString()
    }
}