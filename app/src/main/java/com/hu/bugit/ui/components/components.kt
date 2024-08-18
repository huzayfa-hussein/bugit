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

/**
 * A customizable top app bar for the BugIt app.
 *
 * The top app bar displays a title, an optional navigation icon, and optional actions.
 *
 * @param title The title text to display in the top app bar. Defaults to "BugIt".
 * @param onNavigationIconClick An optional callback function that is invoked when the navigation icon (typically a back arrow) is clicked. If null, the icon is not clickable.
 * @param actions A slot for composable content to be displayed as actions on the right side of the top app bar. Defaults to an empty content.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BugItTopBar(
    title: String = "",
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

/**
 * A customized dialog for displaying success or failure messages.
 * @param isSuccess is a boolean value indicating whether the operation was successful or not.
 * @param message is a string value containing the message to be displayed.
 * @param onDismiss is a lambda function that is invoked when the dialog is dismissed.
 */
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