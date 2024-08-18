package com.hu.bugit.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.saveBitmapToImageUri(context: Context, fileName: String = "screenshot"): Uri? {

    val filesDir = context.cacheDir
    val imageFile = File(filesDir, "${fileName}_${System.currentTimeMillis()}.png")

    return try {
        val outputStream = FileOutputStream(imageFile)
        this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        // Get the Uri for the saved file
        Uri.fromFile(imageFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


fun Uri.getPathFromUri(context: Activity?): String {
    var path = ""
    val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME)
    context?.contentResolver?.query(this, projection, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            path = cursor.getString(columnIndex)
        }
    }
    return path.ifEmpty { this.path ?: "" }
}
