package com.hu.bugit.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.saveBitmapToImageFile(context: Context, fileName: String = "screenshot"): Uri? {
//    return try {
//        val imagesDir =
//            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_images")
//        if (!imagesDir.exists()) {
//            imagesDir.mkdirs()
//        }
//        val file = File(imagesDir, "${fileName}_${System.currentTimeMillis()}.png")
//        val os = FileOutputStream(file)
//        this.compress(Bitmap.CompressFormat.PNG, 100, os)
//        os.flush()
//        os.close()
//        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
//    } catch (e: IOException) {
//        e.printStackTrace()
//        null
//    }

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