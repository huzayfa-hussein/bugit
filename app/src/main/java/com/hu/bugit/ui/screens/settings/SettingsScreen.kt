package com.hu.bugit.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hu.bugit.R
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.components.TitleView
import com.hu.bugit.ui.theme.BugitTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    title: String = "Settings",
    viewModel: SettingsViewModel = viewModel(),
    onBackButtonClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BugItTopBar(
                title = title,
                onNavigationIconClick = {
                    onBackButtonClicked()
                }
            )
        }
    ) { innerPadding ->
        SettingsContent(paddingValues = innerPadding)
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        SettingsSection(title = R.string.select_platform_to_report) {
            PlatformList(
                modifier = modifier,
                data = listOf("Notion", "Google Sheet", "JIRA", "TRELLO")
            )
        }

    }
}

@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    TitleView(title = title)
    content()
}

@Composable
fun PlatformList(
    modifier: Modifier = Modifier,
    data: List<String> = listOf()
) {
    LazyColumn {
        items(data) {
            PlatformView(platform = it)
        }
    }
}

@Composable
fun PlatformView(
    modifier: Modifier = Modifier,
    platform: String,
    isSelected: Boolean = false
) {

    Card(
        enabled = true,
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = platform)

            IconButton(onClick = { /* Handle action icon click */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Action Icon")
            }
        }
    }

}


@Preview
@Composable
fun SettingsPreview() {
    BugitTheme {
        SettingsScreen()
    }
}