package com.example.instagramclonecompose.uiux

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R
import com.example.instagramclonecompose.model.Bottom
import com.example.instagramclonecompose.model.example

@Composable
fun FypScreen(navController: NavController,modifier: Modifier = Modifier) {
    val bottomItem= listOf(
        example("home",R.drawable.ic_launcher_background.toString()),
        example("home",R.drawable.ic_launcher_background.toString()),
        example("home",R.drawable.ic_launcher_background.toString()),
        example("home",R.drawable.ic_launcher_background.toString()),
    )
    LazyColumn {
        items(bottomItem){listItem->
            FypCard(listItem)
        }
    }
}

@Composable
fun FypCard(example: example,modifier: Modifier = Modifier) {
    var isClicked by remember { mutableStateOf(false) }
    Box(modifier.fillMaxWidth().padding(8.dp).paint(
        painter = painterResource(example.image.toInt()), contentScale = ContentScale.FillWidth
    )){
        Column(modifier.padding(8.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom) {
            Spacer(modifier.height(180.dp))
            Row(modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AsyncImage(R.drawable.ic_launcher_background,null,
                        modifier.clip(shape = CircleShape).size(50.dp))
                    Text(example.label)
                }
                Spacer(modifier.width(180.dp))
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    IconButton(onClick = {isClicked=!isClicked}) {
                        Icon(if (isClicked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,null,
                            tint = if (isClicked) Color.Red else Color.Black)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Email,null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Send,null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.MoreVert,null)
                    }
                }
            }
        }

    }
}