package com.hu.bugit.ui.screens.bugForm

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hu.bugit.R
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.components.TitleView
import com.hu.bugit.ui.theme.BugitTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hu.bugit.extensions.saveBitmapToImageFile

@Composable
fun BugFormScreen(
    modifier: Modifier = Modifier,
    viewModel: BugFormViewModel = viewModel(),
    imageUri: Uri? = null,
    onBackButtonClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.updateImageUri(imageUri)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BugItTopBar(
                title = stringResource(id = R.string.bug_form_title),
                onNavigationIconClick = {
                    onBackButtonClicked()
                }
            )
        }
    ) { innerPadding ->
        BugFormContent(
            modifier = modifier,
            paddingValues = innerPadding,
            uiState = uiState,
            onIntent = viewModel::onIntent
        )
    }

}

@Composable
fun BugFormContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: BugFormState = BugFormState(),
    onIntent: (BugFormIntent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BugFormSection(
            title = R.string.bug_title
        ) {
            BugTitle(modifier = modifier, title = uiState.title) {
                onIntent(BugFormIntent.OnTitleChanged(it))
            }
        }

        BugFormSection(
            title = R.string.bug_description_title
        ) {
            BugDescription(modifier = modifier, description = uiState.description) {
                onIntent(BugFormIntent.OnDescriptionChanged(it))
            }
        }
        BugFormSection(
            title = R.string.select_an_image_or_take_a_screenshot
        ) {
            BugCapturing(modifier = modifier, imageUri = uiState.imageUri) {
                onIntent(BugFormIntent.OnImageChanged(it))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))


        SubmitView(modifier = modifier) {
            onIntent(BugFormIntent.Submit)
        }
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
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    onSelectedImageUri: (Uri?) -> Unit = {}
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            onSelectedImageUri(uri)
        }
    )
    val context = LocalContext.current
    Column(modifier = modifier) {
        ImageContainer(imageUri = imageUri)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = {
            photoPicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }) {
            Text(text = "Select Image")
        }

        OutlinedButton(onClick = {
            captureScreenshot(context)?.let {
                it.saveBitmapToImageFile(context).let { uri ->
                    onSelectedImageUri(uri)
                }
            }
        }) {
            Text(text = "Take Screenshot")
        }
    }
}

@Composable
fun BugDescription(
    modifier: Modifier = Modifier,
    description: String = "",
    onDescriptionChanged: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = description,
        minLines = 5,
        maxLines = 5,
        onValueChange = {
            onDescriptionChanged(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.description_placeholder))
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal
        ),
        colors = OutlinedTextFieldDefaults.colors(),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}


@Composable
fun BugTitle(
    modifier: Modifier = Modifier,
    title: String = "",
    onTitleChanged: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = title,
        onValueChange = {
            onTitleChanged(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.title_placeholder))
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal
        ),
        colors = OutlinedTextFieldDefaults.colors(),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}


@Composable
fun SubmitView(
    modifier: Modifier = Modifier,
    onSubmitClicked: () -> Unit = {}
) {
    Button(
        onClick = { onSubmitClicked() },
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
    imageUri: Uri? = null
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
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageUri)
                    .allowHardware(false).build(),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            ),
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(200.dp)
        )
    }
}

fun captureScreenshot(context: Context): Bitmap? {
    val window = (context as? Activity)?.window ?: return null
    val view = window.decorView.rootView
    view.isDrawingCacheEnabled = true
    val bitmap = Bitmap.createBitmap(view.drawingCache)
    view.isDrawingCacheEnabled = false
    return bitmap
}


@Preview
@Composable
fun BugFormPreview() {
    BugitTheme {
        BugFormScreen()
    }
}