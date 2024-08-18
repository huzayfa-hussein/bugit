package com.hu.bugit.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

@Composable
fun ResultDialog(
    modifier: Modifier = Modifier,
    isSuccess: Boolean = false,
    message: String,
    onDismiss: () -> Unit = {}
) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(id = if (isSuccess) R.string.success else R.string.failed),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                color = if (isSuccess) Color.Green else Color.Red
            )
            Text(
                text = message,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp)
            )

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(id = if (isSuccess) R.string.okay else R.string.try_again))
            }

        }

    }

}

@Preview
@Composable
fun ComponentsPreview() {
    ResultDialog(message = "You have reported a new bug successfully!")
}