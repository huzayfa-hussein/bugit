package com.hu.bugit.ui.components

import android.icu.text.CaseMap.Title
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hu.bugit.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BugItTopBar(
    title: String = "BugIt",
    onNavigationIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = title)
        },
        actions = actions,
        navigationIcon =
        {
            IconButton(onClick = { onNavigationIconClick?.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }

    )

}

/**
 * TitleView is a Composable function responsible for displaying a title view.
 * It takes the [modifier] and [title] resource ID as parameters.
 */
@Composable
fun TitleView(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium
        )

    }
}

@Preview
@Composable
fun ComponentsPreview() {
    TitleView(title = R.string.bug_description_title)
}