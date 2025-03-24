package com.example.instagramclonecompose.uiux

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instagramclonecompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController,modifier: Modifier = Modifier) {
    val context= LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){selectedUri->
        selectedUri?.let {
            uri=it

            uri?.let {uris->
                if (Build.VERSION.SDK_INT < 28){
                    bitmap= Media.getBitmap(context.contentResolver,uris)
                }
                else{
                    val source= ImageDecoder.createSource(context.contentResolver,uris)
                    bitmap= ImageDecoder.decodeBitmap(source)
                }
            }
        }
    }
    val cameraLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ){capturedBitmap->
        capturedBitmap?.let {
            bitmap=it
        }
    }
    var isExpanded by remember { mutableStateOf(false) }
    var fullname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    val bottomSheetState= rememberModalBottomSheetState()
    var isExposed by remember { mutableStateOf(false) }
    val genderList = listOf("Male","Female")
    var gender by remember { mutableStateOf(genderList[0]) }

    if (isExpanded){
        ModalBottomSheet(
            onDismissRequest = {isExpanded=false},
            modifier = modifier.fillMaxSize(),
            sheetState = bottomSheetState,
        ) {
            Column(modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = {launcher.launch("image/*")}) {
                    Text("Choose From Gallery", color = Color.Black)
                }
                TextButton(onClick = {cameraLauncher.launch(null)}) {
                    Text("Take Picture", color = Color.Black)
                }
                TextButton(onClick = {}) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }

    Row(modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {navController.navigate("Account")}) {
            Icon(Icons.Default.ArrowBack,null)
        }
        Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
    Spacer(modifier.height(20.dp))
    Column(modifier.padding(top = 50.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        AsyncImage(bitmap?.asImageBitmap(),null,
            modifier.clip(shape = CircleShape).size(75.dp))
        TextButton(onClick = {isExpanded=true}) {
            Text("Edit Foto")
        }
        OutlinedTextField(
            value = fullname,
            onValueChange = {
                fullname=it
            },
            modifier.fillMaxWidth().padding(4.dp),
            label = { Text("Fullname") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = username,
            onValueChange = {
                username=it
            },
            modifier.fillMaxWidth().padding(4.dp),
            label = { Text("username") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = bio,
            onValueChange = {
                bio=it
            },
            modifier.fillMaxWidth().padding(4.dp),
            label = { Text("Bio") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        ExposedDropdownMenuBox(
            expanded = isExposed,
            onExpandedChange = {isExposed=!isExposed},
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                modifier=Modifier.menuAnchor().fillMaxWidth().padding(4.dp),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(isExposed)}
            )
            ExposedDropdownMenu(
                expanded = isExposed,
                onDismissRequest = {isExposed=false},
            ) {
                genderList.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(s) },
                        onClick = {
                            gender=s
                            isExposed=false
                        },
                        modifier.fillMaxWidth().padding(4.dp)
                    )
                }
            }
        }
        Button(onClick = {navController.navigate("Account")},modifier.fillMaxWidth().padding(4.dp)) {
            Text("Save")
        }
    }
}