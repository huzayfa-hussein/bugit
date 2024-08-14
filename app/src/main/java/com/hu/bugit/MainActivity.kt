package com.hu.bugit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.hu.bugit.ui.components.BugItNavigation
import com.hu.bugit.ui.theme.BugitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val imageUri = handleReceivedImage(intent)
        setContent {
            BugitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    BugItNavigation()
                }
            }
        }
    }

    private fun handleReceivedImage(intent: Intent?): Uri? {
        if (intent?.action == Intent.ACTION_SEND) {
            return intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        return null
    }
}