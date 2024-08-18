package com.hu.bugit.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hu.bugit.R
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.components.TitleView
import com.hu.bugit.ui.theme.BugitTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.settings.PlatformSetting

/**
 * Represents the settings screen.
 */
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
        SettingsContent(
            paddingValues = innerPadding,
            uiState = uiState,
            onIntent = viewModel::onIntent
        )
    }
}

/**
 * Represents the content of the settings screen.
 * @param paddingValues The padding values to be applied to the content.
 * @param uiState The current state of the settings screen.
 * @param onIntent The callback function to handle user intents.
 */
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: SettingsState = SettingsState(),
    onIntent: (SettingsIntent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        SettingsSection(title = R.string.select_platform_to_report) {
            Text(
                text = stringResource(R.string.note_only_notion_is_available_at_the_moment),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            PlatformList(
                modifier = modifier,
                data = uiState.platformList
            ) {
                onIntent(SettingsIntent.OnPlatformSelected(it))
            }
        }

    }
}

/**
 * Represents a section of the settings screen.
 * @param title The title of the section.
 * @param content The content of the section.
 */
@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    TitleView(title = title, modifier = modifier.padding(top = 16.dp))
    content()
}

/**
 * Represents a list of platform settings.
 * @param modifier The modifier to be applied to the list.
 * @param data The list of platform settings.
 * @param onPlatformSelected The callback function to handle platform selection.
 */
@Composable
fun PlatformList(
    modifier: Modifier = Modifier,
    data: List<PlatformSetting> = listOf(),
    onPlatformSelected: (BugPlatform) -> Unit = {}
) {
    LazyColumn {
        items(data) {
            PlatformView(modifier = modifier, platform = it)
        }
    }
}

/**
 * Represents a single platform setting.
 * @param platform The platform setting.
 * @param onPlatformSelected The callback function to handle platform selection.
 */
@Composable
fun PlatformView(
    modifier: Modifier = Modifier,
    platform: PlatformSetting,
    onPlatformSelected: (BugPlatform) -> Unit = {}
) {

    Card(
        enabled = platform.isSelected,
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(),
        onClick = { onPlatformSelected(platform.platform) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = platform.platform.name)

            IconButton(onClick = { onPlatformSelected(platform.platform) }) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Action Icon",
                    tint = if (platform.isSelected) Color.Green else Color.Gray
                )
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