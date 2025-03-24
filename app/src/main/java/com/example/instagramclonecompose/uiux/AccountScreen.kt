package com.example.instagramclonecompose.uiux

import android.net.Uri
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R

@Composable
fun AccountScreen(navController: NavController, modifier: Modifier = Modifier) {

    Column(modifier.padding(16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Username", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
                Text("100")
                Text("Followers")
            }
            Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceBetween) {
                Text("100")
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
            }
        }
    }
}
