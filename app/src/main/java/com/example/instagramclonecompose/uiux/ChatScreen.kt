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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.instagramclonecompose.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(navController: NavController,idReceiver:String,modifier: Modifier = Modifier) {
    var message by remember { mutableStateOf("") }
    val firebaseAuth= FirebaseAuth.getInstance()
    val firebaseDatabase= FirebaseDatabase.getInstance().getReference("chat")
    val userDatabase=FirebaseDatabase.getInstance().getReference("user")
    val idSender=firebaseAuth.currentUser?.uid
    val chatList = remember { mutableStateListOf<Chat?>() }
    val keyboardController= LocalSoftwareKeyboardController.current
    var usernameReceiver by remember { mutableStateOf("") }

    firebaseDatabase.orderByChild("time").addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                chatList.clear()
                for (i in snapshot.children){
                    val chat=i.getValue(Chat::class.java)
                    if (chat != null &&
                        (chat.idSender == idSender && chat.idReceiver == idReceiver ||
                                chat.idSender == idReceiver && chat.idReceiver == idSender)) {
                        chatList.add(chat)
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }
    })

    userDatabase.child(idReceiver).addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                var user=snapshot.getValue(User::class.java)
                if (user!=null){
                    usernameReceiver=user.username
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    })

    Scaffold(
        bottomBar = {
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message=it
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
                        sendMessage(idSender,idReceiver,message)
                        message=""
                    },
                    ) {
                        Icon(Icons.Outlined.Send,null)
                    }
                }
            )
        }
    ) {
        Column(modifier.padding(8.dp)) {
            Row(modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {navController.navigate("User")}) {
                    Icon(Icons.Outlined.ArrowBack,null)
                }
                Text(usernameReceiver, fontWeight = FontWeight.Black, fontSize = 20.sp, textAlign = TextAlign.Center)
                Spacer(modifier.width(100.dp))
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Phone,null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Call,null)
                }
            }
            LazyColumn {
                items(chatList){
                    if (it != null) {
                        chatCard(it)
                    }
                }
            }
        }
    }
}

fun sendMessage(idSender: String?, idReceiver: String,message:String) {
    val firebaseDatabase= FirebaseDatabase.getInstance().getReference("chat")
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
    val chat= idSender?.let { Chat(message, it,idReceiver,time) }
    val chatId = "chat $idSender $idReceiver $time "
    firebaseDatabase.child(chatId).setValue(chat)
}

@Composable
fun chatCard(chat: Chat,modifier: Modifier=Modifier){
    val firebaseAuth=FirebaseAuth.getInstance()
    val idSender=firebaseAuth.currentUser?.uid
    Row(modifier.fillMaxWidth().padding(8.dp), horizontalArrangement =
    if (chat.idSender==idSender) Arrangement.End else Arrangement.Start) {
        Box(modifier.background(if (chat.idSender==idSender) Color.Green else Color.Gray).padding(12.dp)){
            Text(chat.message, fontSize = 15.sp, fontWeight = FontWeight.Black, color = Color.Black)
        }
    }
}
