package com.example.instagramclonecompose.uiux

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R
import com.example.instagramclonecompose.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UserScreen(navController: NavController,modifier: Modifier = Modifier) {
    val firebaseAuth=FirebaseAuth.getInstance()
    val firebaseDatabase=FirebaseDatabase.getInstance().getReference("user")
    val uid=firebaseAuth.currentUser?.uid
    val userList = remember { mutableStateListOf<User>() }

    firebaseDatabase.addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                userList.clear()
                for (i in snapshot.children){
                    val user=i.getValue(User::class.java)
                    if (user!=null && user.id!=uid){
                        userList.add(user)
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }
    })
    LazyColumn(modifier.padding(8.dp)) {
        items(userList){
            UserCard(
                user = it,
                onClick = {
                    navController.navigate("Chat/${it.id}")
                },
            )
        }
    }
}

@Composable
fun UserCard(user: User,onClick : ()->Unit,modifier: Modifier = Modifier) {
    Row(modifier.padding(16.dp).fillMaxWidth().clickable {
        onClick.invoke()
    }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AsyncImage(R.drawable.img,null, contentScale = ContentScale.FillBounds,
            modifier = Modifier.clip(shape = CircleShape).size(75.dp).clickable {
                onClick.invoke()
            }
        )
        Text(user.username, fontSize = 20.sp, fontWeight = FontWeight.Medium,modifier=modifier.clickable {
            onClick.invoke()
        })
    }
}