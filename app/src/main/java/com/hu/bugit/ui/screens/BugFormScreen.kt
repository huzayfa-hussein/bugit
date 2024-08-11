package com.hu.bugit.ui.screens

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hu.bugit.R
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.components.TitleView
import com.hu.bugit.ui.theme.BugitTheme

@Composable
fun BugFormScreen(
    modifier: Modifier = Modifier,
    title: String = "Create New Bug"
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BugItTopBar(
                title = title,
                onNavigationIconClick = {

                }
            )
        }
    ) { innerPadding ->
        BugFormContent(paddingValues = innerPadding)
    }

}

@Composable
fun BugFormContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        BugFormSection(
            title = R.string.select_an_image_or_take_a_screenshot
        ) {
            BugCapturing(modifier = modifier)
        }
        Spacer(modifier = Modifier.height(8.dp))

        BugFormSection(
            title = R.string.bug_description_title
        ) {
            BugDescription(modifier = modifier)
        }
        SubmitView(modifier = modifier)
    }
}

@Composable
fun BugFormSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TitleView(title = title, modifier = modifier)
    content()

}

@Composable
fun BugCapturing(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ImageContainer(imageUri = Uri.parse(""))
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Select Image")
        }

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Take Screenshot")
        }
    }
}

@Composable
fun BugDescription(
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.height(12.dp))
    OutlinedTextField(
        value = description,
        onValueChange = {
            description = it
        },
        placeholder = {
            Text(text = stringResource(id = R.string.description_placeholder), color = Color.Blue)
        },
        textStyle = TextStyle(
            color = Color.Blue,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Blue,
            unfocusedBorderColor = Color.Blue,
            unfocusedLabelColor = Color.Blue,
            unfocusedLeadingIconColor = Color.Blue
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .defaultMinSize(minHeight = 200.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun SubmitView(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.submit_label),
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    imageUri: Uri
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(16.dp)
            )
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}


@Preview
@Composable
fun BugFormPreview() {
    BugitTheme {
        BugFormScreen()
    }
}