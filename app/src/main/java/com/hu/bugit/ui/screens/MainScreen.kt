package com.hu.bugit.ui.screens

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hu.bugit.R
import com.hu.bugit.domain.models.BugPlatform
import com.hu.bugit.ui.components.BugItTopBar
import com.hu.bugit.ui.theme.BugitTheme


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    title: String = "Bug Screen"
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BugItTopBar(
                title = title,
                actions = {
                    IconButton(onClick = { /* Handle action icon click */ }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Action Icon")
                    }
                    IconButton(onClick = { /* Handle action icon click */ }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Action Icon")
                    }
                }
            )
        }
    ) { innerPadding ->
        MainScreenContent(paddingValues = innerPadding)
    }


}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = { /* do something */ },
            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
        ) {
            Icon(Icons.Filled.Add, "Localized description")
        }

        BugCard()
        BugCard()
        BugCard()
        BugCard()
    }
}

@Composable
fun BugCard(
    modifier: Modifier = Modifier,
    @DrawableRes imageUrl: Int = R.drawable.ic_launcher_foreground,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageUrl),
                contentDescription = "Bug Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
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
                        text = "#1",
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
                    text = "Bug Title",
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
fun MainScreenPreview(

) {
    BugitTheme {
        MainScreen()
    }
}