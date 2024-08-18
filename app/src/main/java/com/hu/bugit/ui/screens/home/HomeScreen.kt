package com.hu.bugit.ui.screens.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.hu.bugit.R
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.domain.models.home.BugModel
import com.hu.bugit.extensions.openBrowser
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.theme.BugitTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAddIconButtonClicked: () -> Unit = {},
    onSettingsIconButtonClicked: () -> Unit = {},
    onImageReceived: (Uri?) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalContext.current as Activity
    val intent = activity.intent
    LaunchedEffect(key1 = intent) {
        if (Intent.ACTION_SEND == intent.action) {
            val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            if (imageUri != null && !uiState.isImageReceived) {
                onImageReceived(imageUri)
                viewModel.onIntent(HomeIntent.OnImageReceived)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BugItTopBar(
                title = stringResource(id = R.string.bug_screen_title),
                actions = {
                    IconButton(onClick = { onAddIconButtonClicked() }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Action Icon")
                    }
                    IconButton(onClick = { viewModel.onIntent(HomeIntent.OnRefreshBugList) }) {
                        Icon(Icons.Outlined.Refresh, contentDescription = "Action Icon")
                    }
                    IconButton(onClick = { onSettingsIconButtonClicked() }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Action Icon")
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            paddingValues = innerPadding,
            navController = navController,
            uiState = uiState,
            onIntent = { intent ->
                when (intent) {
                    is HomeIntent.OnBugItemClicked -> {
                        activity.openBrowser(intent.url)
                    }

                    is HomeIntent.OnCreateBugClicked -> {
                        onAddIconButtonClicked()
                    }

                    else -> Unit
                }
                viewModel.onIntent(intent)
            }
        )
    }


}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    uiState: HomeState = HomeState(),
    onIntent: (HomeIntent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        if (uiState.bugList.isEmpty()) {
            NewBugView(modifier = modifier) {
                onIntent(HomeIntent.OnCreateBugClicked)
            }
        } else {
            BugCardList(
                modifier = modifier,
                bugList = uiState.bugList,
            ) { url ->
                onIntent(HomeIntent.OnBugItemClicked(url))
            }
        }
    }
}

@Composable
fun BugCardList(
    modifier: Modifier = Modifier,
    bugList: List<BugModel> = listOf(),
    onBugClicked: (url: String) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {

        items(bugList) { bugModel ->
            BugCard(bugModel = bugModel, onBugClicked = onBugClicked)
        }
    }
}

@Composable
fun BugCard(
    modifier: Modifier = Modifier,
    bugModel: BugModel,
    onBugClicked: (url: String) -> Unit = {}
) {
    Card(
        onClick = { onBugClicked(bugModel.url) },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(bugModel.imageUrl),
                contentDescription = "Bug Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Bug Number
                    Text(
                        text = "#${bugModel.id}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )

                    // Source Indicator
                    SourceIndicator(BugPlatform.NOTION)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Bug Title
                Text(
                    text = bugModel.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )

                Text(
                    text = bugModel.date,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun NewBugView(
    modifier: Modifier = Modifier,
    onAddNewBugClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = stringResource(id = R.string.empty_bug_list_text))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_no_bug), contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(id = R.string.report_your_first_bug))
        Spacer(modifier = Modifier.height(24.dp))
        FloatingActionButton(onClick = { onAddNewBugClicked() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}

@Composable
fun SourceIndicator(source: BugPlatform) {
    val sourceText = when (source) {
        BugPlatform.NOTION -> "Notion"
        BugPlatform.GOOGLE_SHEET -> "Google Sheets"
        BugPlatform.JIRA -> TODO()
        BugPlatform.TRELLO -> TODO()
    }
    val sourceColor = when (source) {
        BugPlatform.NOTION -> Color(0xFF4CAF50)
        BugPlatform.GOOGLE_SHEET -> Color(0xFF2196F3)
        BugPlatform.JIRA -> TODO()
        BugPlatform.TRELLO -> TODO()
    }

    Box(
        modifier = Modifier
            .background(sourceColor, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = sourceText,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
        )
    }
}


@Preview
@Composable
fun HomeScreenPreview(

) {
    BugitTheme {
        NewBugView()
    }
}