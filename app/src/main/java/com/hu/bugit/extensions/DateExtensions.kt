package com.hu.bugit.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
const val DATE_FORMAT = "dd-MM-yyyy"

fun Date.convertToDateString(format: String = DATE_FORMAT): String {
    val formatDate = SimpleDateFormat(format, Locale.getDefault())
    return formatDate.format(this)
}