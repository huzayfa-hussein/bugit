package com.hu.bugit.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openBrowser(url: String?) {
    try {
        // Create an Intent to view the URL in a web browser.
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        // Start the web browser activity.
        this.startActivity(browserIntent)
    } catch (e: Exception) {

        // Print any exceptions that occur.
        e.printStackTrace()
    }
}