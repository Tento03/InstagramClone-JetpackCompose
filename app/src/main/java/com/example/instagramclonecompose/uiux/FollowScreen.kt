package com.example.instagramclonecompose.uiux

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.google.gson.Gson

@SuppressLint("RememberReturnType")
@Composable
fun FollowScreen(navController: NavController, userArgs: String, modifier: Modifier = Modifier) {
    val context= LocalContext.current
    val user = Gson().fromJson(userArgs, User::class.java)
    val firebaseAuth=FirebaseAuth.getInstance()
    val uid=firebaseAuth.currentUser?.uid
    val followingRef=FirebaseDatabase.getInstance().getReference("Follow")
       .child(user.id) .child("Followings")
    val followersRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(user.id).child("Followers")
    var isFollowing by remember { mutableStateOf(false) }
    var followingList by remember { mutableStateOf(0) }
    var followersList by remember { mutableStateOf(0) }

    followingRef.child(user.id).addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                isFollowing=true
            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    })
    val followersCount = remember {
        FirebaseDatabase.getInstance().getReference("Follow")
            .child(user.id).child("Followers")
        followersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    followersList = snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load followers", Toast.LENGTH_SHORT).show()
            }
        })
    }

    val followingCount = remember {
        FirebaseDatabase.getInstance().getReference("Follow")
            .child(user.id).child("Followings")
        followingRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    followingList = snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load followings", Toast.LENGTH_SHORT).show()
            }
        })
    }


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigate("Home") }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(user.username, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

            Row {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = R.drawable.img,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(75.dp)
            )
            Column {
                Text(user.username, fontWeight = FontWeight.Medium, fontSize = 20.sp)
                Spacer(modifier = Modifier.size(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("100", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Posts", fontSize = 14.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(followersList.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Followers", fontSize = 14.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(followingList.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Following", fontSize = 14.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (isFollowing){
                        UnfollowUser(user.id,context)
                        isFollowing=false
                    }
                    else{
                        FollowUser(user.id,context)
                        isFollowing=true
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(if (isFollowing) "Following" else "Follow", fontSize = 16.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.Gray),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text("Kirim Pesan", fontSize = 16.sp)
            }
        }
        ViewPager()
    }
}

fun FollowUser(idReceiver: String,context:Context) {
    val firebaseAuth=FirebaseAuth.getInstance()
    val uid=firebaseAuth.currentUser?.uid
    val followingRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(uid!!).child("Followings")
    val followersRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(idReceiver).child("Followers")
    followingRef.child(idReceiver).setValue(true).addOnCompleteListener{task->
        if (task.isSuccessful){
            followersRef.child(uid).setValue(true).addOnCompleteListener{innerTask->
                if (innerTask.isSuccessful){
                    Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

fun UnfollowUser(idReceiver: String, context: Context) {
    val firebaseAuth=FirebaseAuth.getInstance()
    val uid=firebaseAuth.currentUser?.uid
    val followingRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(uid!!).child("Followings")
    val followersRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(idReceiver).child("Followers")
    followingRef.child(idReceiver).removeValue().addOnCompleteListener{task->
        if (task.isSuccessful){
            followersRef.child(uid).removeValue().addOnCompleteListener{innerTask->
                if (innerTask.isSuccessful){
                    Toast.makeText(context, "UnFollowed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Composable
fun ViewPager(modifier: Modifier = Modifier) {
    val listTab= listOf(Icons.Outlined.Menu,Icons.Outlined.AccountBox)
    var pagerState= rememberPagerState(
        initialPage = 0,
        pageCount = { listTab.size }
    )
    var targetPage by remember { mutableStateOf(-1) }

    Column(modifier.padding(8.dp)) {
        ScrollableTabRows(
            listTab = listTab,
            pagerState = pagerState,
            onTabSelected = {index->
                targetPage=index
            }
        )
        HorizontalPager(
            state = pagerState,
        ) {page: Int ->
            when(page){
                0->Posted()
                1->Tagged()
            }
        }
    }
    LaunchedEffect(targetPage) {
        if (targetPage!=-1){
            pagerState.animateScrollToPage(targetPage)
            targetPage=-1
        }
    }
}

@Composable
fun Posted() {

}

@Composable
fun Tagged() {

}

@Composable
fun ScrollableTabRows(
    listTab : List<ImageVector>,
    pagerState: PagerState,
    onTabSelected : (Int) -> Unit
) {
    val scrollState= rememberScrollState()
    Row(modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceAround) {
        listTab.forEachIndexed { index, imageVector ->
            val isSelected=pagerState.currentPage==index
            Column(modifier = Modifier.padding(8.dp).clickable {
                onTabSelected.invoke(index)
            },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector,null, modifier = Modifier.size(50.dp))
                if (isSelected){
                    Box(modifier = Modifier.width(40.dp).height(2.dp).background(Color.Black))
                }
            }
        }
    }
}
