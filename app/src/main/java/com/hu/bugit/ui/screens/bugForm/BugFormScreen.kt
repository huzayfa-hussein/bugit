package com.hu.bugit.ui.screens.bugForm

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hu.bugit.R
import com.hu.bugit.extensions.getPathFromUri
import com.hu.bugit.extensions.saveBitmapToImageUri
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.components.ResultDialog
import com.hu.bugit.ui.components.TitleView
import com.hu.bugit.ui.theme.BugitTheme

/**
 * Represents the bug form screen.
 * @param modifier The modifier to be applied to the screen.
 * @param viewModel The view model associated with the screen.
 * @param imageUri The URI of the captured image.
 * @param onBackButtonClicked The callback function to handle back button click.
 */
@Composable
fun BugFormScreen(
    modifier: Modifier = Modifier,
    viewModel: BugFormViewModel = hiltViewModel(),
    imageUri: Uri? = null,
    onBackButtonClicked: () -> Unit = {}
) {
    val activity = LocalContext.current as? Activity
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(imageUri) {
        viewModel.updateImageUri(imageUri, imageUri?.getPathFromUri(activity) ?: "")
    }
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            BugFormContent(
                modifier = modifier,
                paddingValues = innerPadding,
                uiState = uiState,
                onIntent = { intent ->
                    when (intent) {
                        is BugFormIntent.OnDismissDialog -> {
                            if (intent.success) {
                                onBackButtonClicked()
                            }
                        }

                        else -> Unit
                    }
                    viewModel.onIntent(intent)
                }
            )
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(52.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }
}

/**
 * Represents the content of the bug form screen.
 * @param paddingValues The padding values to be applied to the content.
 * @param uiState The current state of the bug form.
 * @param onIntent The callback function to handle user intents.
 */
@Composable
fun BugFormContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: BugFormState = BugFormState(),
    onIntent: (BugFormIntent) -> Unit = {}
) {
    val activity = LocalContext.current as? Activity
    if (uiState.showDialog) {
        ResultDialog(
            message = uiState.dialogMessage,
            isSuccess = uiState.isSubmitted
        ) {
            onIntent(BugFormIntent.OnDismissDialog(uiState.isSubmitted))
        }
    }
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
                onIntent(BugFormIntent.OnImageChanged(it, it?.getPathFromUri(activity) ?: ""))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))


        SubmitView(modifier = modifier) {
            onIntent(BugFormIntent.Submit)
        }
    }
}

/**
 * Represents a section of the bug form.
 * @param title The title of the section.
 * @param modifier The modifier to be applied to the section.
 * @param content The content of the section.
 **/
@Composable
fun BugFormSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TitleView(title = title, modifier = modifier)
    content()

}

/**
 * Represents the bug capturing section.
 * @param imageUri The URI of the captured image.
 * @param onSelectedImageUri The callback function to handle selected image URI.
 */
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
            Text(text = stringResource(R.string.select_image))
        }

        OutlinedButton(onClick = {
            captureScreenshot(context)?.let {
                onSelectedImageUri(it.saveBitmapToImageUri(context))
            }
        }) {
            Text(text = stringResource(R.string.take_screenshot))
        }
    }
}

/**
 * Represents the bug description input field.
 * @param description The description of the bug.
 * @param onDescriptionChanged The callback function to handle description changes.
 */
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

/**
 * Represents the bug title input field.
 * @param title The title of the bug.
 * @param onTitleChanged The callback function to handle title changes.
 */
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

/**
 * Represents the submit button.
 * @param onSubmitClicked The callback function to handle the submit button click.
 */
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

/**
 * Represents the image container.
 * @param imageUri The URI of the image.
 */
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
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (imageUri == null) painterResource(id = R.drawable.ic_image_place_holder) else rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageUri)
                    .allowHardware(false).build(),
                placeholder = painterResource(id = R.drawable.ic_image_place_holder)
            ),
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(150.dp)
        )
    }
}

/**
 * Captures a screenshot of the current screen.
 */
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
        ImageContainer()
    }
}