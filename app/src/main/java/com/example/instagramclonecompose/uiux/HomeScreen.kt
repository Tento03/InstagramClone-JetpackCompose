package com.example.instagramclonecompose.uiux

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R
import com.example.instagramclonecompose.ui.theme.InstagramCloneComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController,modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Instagram Clone") },
                actions = {
                    IconButton(
                        onClick = {navController.navigate("")}
                    ) {
                        Icon(Icons.Outlined.FavoriteBorder,null)
                    }
                    IconButton(
                        onClick = {navController.navigate("Chat")}) {
                        Icon(Icons.Filled.Send,null)
                    }
                },
            )
        },
    ) {
        LazyColumn(modifier.padding(8.dp)) {
            item {
                HomeCard(navController)
                HomeCard(navController)
                HomeCard(navController)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(navController: NavController,modifier: Modifier = Modifier) {
    var isClicked by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState()
    var isExpanded by remember { mutableStateOf(false) }

    if (isExpanded){
        ModalBottomSheet(
            onDismissRequest = {isExpanded=false},
            modifier = Modifier.fillMaxSize(),
            sheetState = modalBottomSheetState,
        ) { }
    }

    Column(modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween) {
        Row(modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = R.drawable.ic_launcher_background,
                contentDescription = null,
                modifier
                    .clip(shape = CircleShape)
                    .size(60.dp)
            )
            Text("Username")
        }
        AsyncImage(model = R.drawable.ic_launcher_background,
            contentDescription = null,
            modifier
                .fillMaxWidth()
                .size(200.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = {isClicked=!isClicked}) {
                Icon(if (isClicked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,null,
                    tint = if (isClicked) Color.Red else Color.Black)
            }
            IconButton(onClick = {
                isExpanded=true
            }) {
                Icon(Icons.Outlined.Email,null)
            }
            IconButton(onClick = {navController.navigate("Chat")}) {
                Icon(Icons.Outlined.Send,null)
            }
        }
    }
}
