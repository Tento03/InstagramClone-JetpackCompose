package com.example.instagramclonecompose.uiux

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclonecompose.model.Chat

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(navController: NavController,modifier: Modifier = Modifier) {
    var chatList= arrayListOf(
        Chat("hi",true),
        Chat("hi",false),
        Chat("hi",true),
        Chat("hi",false),
        Chat("hi",true),
    )
    var chat by remember { mutableStateOf("") }
    val keyboardController= LocalSoftwareKeyboardController.current

    Scaffold(
        bottomBar = {
            OutlinedTextField(
                value = chat,
                onValueChange = {
                    chat=it
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier=Modifier.padding(8.dp).fillMaxWidth(),
                placeholder = { Text("Message...") },
                trailingIcon = {
                    IconButton(onClick = {
                        chatList.add(Chat(chat,true))
                        chat=""
                    }, enabled = if (chat.isNotEmpty()) true else false
                    ) {
                        Icon(Icons.Outlined.Send,null)
                    }
                }
            )
        }
    ) {
        Column(modifier.padding(8.dp)) {
            Row(modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {navController.navigate("")}) {
                    Icon(Icons.Outlined.ArrowBack,null)
                }
                Text("Username", fontWeight = FontWeight.Black, fontSize = 20.sp, textAlign = TextAlign.Center)
                Spacer(modifier.width(100.dp))
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Phone,null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Call,null)
                }
            }
            LazyColumn(modifier.padding(8.dp)) {
                items(chatList){
                    ChatCard(it)
                }
            }
        }
    }
}

@Composable
fun ChatCard(chat: Chat,modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = if (chat.isSender) Arrangement.End else Arrangement.Start) {
        Box(modifier.background(if (chat.isSender) Color.Green else Color.Gray).padding(12.dp)){
            Text(chat.message, color = Color.Black)
        }
    }
}