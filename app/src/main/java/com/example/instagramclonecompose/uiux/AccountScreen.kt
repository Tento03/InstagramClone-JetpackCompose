package com.example.instagramclonecompose.uiux

import android.net.Uri
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Icon
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
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
fun AccountScreen(navController: NavController, modifier: Modifier = Modifier) {
    var isOpened by remember { mutableStateOf(false) }
    val firebaseAuth=FirebaseAuth.getInstance()
    val uid=firebaseAuth.currentUser?.uid
    var username by remember { mutableStateOf("") }
    var followingList by remember { mutableStateOf(0) }
    var followersList by remember { mutableStateOf(0) }
    val userDatabase=FirebaseDatabase.getInstance().getReference("user").child(uid!!)
        .addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var user=snapshot.getValue(User::class.java)
                    if (user!=null){
                        username=user.username
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    val followingRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(uid).child("Followings").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    followingList=snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    val followersRef=FirebaseDatabase.getInstance().getReference("Follow")
        .child(uid).child("Followers").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    followersList=snapshot.childrenCount.toInt()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    Column(modifier.padding(16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(username, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AsyncImage(
                model = R.drawable.ic_launcher_background,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(60.dp)
            )
            Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text("100")
                Text("Post")
            }
            Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text(followersList.toString())
                Text("Followers")
            }
            Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text(followingList.toString())
                Text("Followings")
            }
        }

        Column(modifier = Modifier.padding(start = 15.dp, top = 5.dp)) {
            Text("Programmer")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {navController.navigate("EditProfile")},
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("Edit Profile")
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("Share Profile")
                }
                Button(
                    onClick = {
                        isOpened=true},
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("Log Out")
                }
            }
            ViewPager(navController)
        }

        if (isOpened){
            AlertDialog(
                onDismissRequest = {isOpened=false},
                title = { Text("Warning") },
                text = { Text("Are You Sure To Log Out?") },
                confirmButton = {
                    TextButton(onClick = {
                        val firebaseAuth=FirebaseAuth.getInstance()
                        firebaseAuth.signOut()
                        navController.navigate("Login"){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive=true
                            }
                        }
                    }) {
                        Text("Confirm", color = Color.Blue)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isOpened=false
                    }) {
                        Text("Cancel", color = Color.Red)
                    }
                }
            )
        }
    }
}

@Composable
fun ViewPager(navController: NavController,modifier: Modifier = Modifier) {
    val tabList= listOf(Icons.Outlined.List,Icons.Default.AccountBox)
    val pagerState = rememberPagerState(
        initialPage =0,
        pageCount = { tabList.size }
    )
    var targetPage by remember { mutableStateOf(-1) }

    Column(modifier.fillMaxSize()) {
        ScrollableTabRow(
            tabList = tabList,
            pagerState = pagerState,
            onTabSelected = {index->
                targetPage=index
            }
        )
        HorizontalPager(
            state = pagerState,
            modifier = modifier.weight(1f)
        ) { page->
            when (page){
                0->PostScreen()
                1->TagScreen()
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
fun PostScreen() {

}

@Composable
fun TagScreen() {

}

@Composable
fun ScrollableTabRow(
    tabList:List<ImageVector>,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit
) {
   val horizontalScroll= rememberScrollState()
   Row(modifier = Modifier
       .fillMaxWidth()
       .horizontalScroll(horizontalScroll),
       horizontalArrangement = Arrangement.SpaceAround
   ) {
       tabList.forEachIndexed { index, imageVector ->
           var isSelected=pagerState.currentPage==index
           Column(modifier = Modifier
               .clickable {
                   onTabSelected.invoke(index)
               }
               .padding(8.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Icon(imageVector,null, modifier = Modifier.size(50.dp).clickable {
                   onTabSelected(index)
               }
               )
               if (isSelected){
                   Box(modifier = Modifier.width(50.dp).height(2.dp).background(Color.Black))
               }
           }
       }
   }
}
