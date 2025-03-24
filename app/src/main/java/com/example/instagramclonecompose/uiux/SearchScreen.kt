package com.example.instagramclonecompose.uiux

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController,modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val keyboardController= LocalSoftwareKeyboardController.current

    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) {
        Column(modifier.padding(4.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query=it
                },
                modifier.fillMaxWidth().padding(4.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    }
                ),
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search,null) }
            )
            LazyColumn {
                item {
                    SearchCard()
                }
            }
        }

    }
}

@Composable
fun SearchCard(modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        Row(modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            AsyncImage(R.drawable.ic_launcher_background,null,
                modifier.clip(shape = CircleShape).size(40.dp))
            Text("Username")
        }
    }
}