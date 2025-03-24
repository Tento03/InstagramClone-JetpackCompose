package com.example.instagramclonecompose.uiux

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddScreen(navController: NavController,modifier: Modifier = Modifier) {
    val context= LocalContext.current
    var description by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var uri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ selectedUri->
        selectedUri?.let {
            uri=it

            uri?.let {uris->
                if (Build.VERSION.SDK_INT < 28){
                    bitmap=Media.getBitmap(context.contentResolver,uris)
                }
                else{
                    val source=ImageDecoder.createSource(context.contentResolver, uris)
                    bitmap=ImageDecoder.decodeBitmap(source)
                }
            }
        }
    }
    val cameraLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ){ capturedBitmap->
        capturedBitmap?.let {
            bitmap=it
        }
    }

    LazyColumn {
        item{
            Column(modifier.padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                Button(onClick = {launcher.launch("image/*")},modifier.fillMaxWidth().padding(4.dp)) {
                    Text("Get From Gallery")
                }
                Button(onClick = {cameraLauncher.launch(null)},modifier.fillMaxWidth().padding(4.dp)) {
                    Text("Open Camera")
                }
                bitmap?.let {
                    Image(it.asImageBitmap(),null,modifier.fillMaxWidth().padding(10.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description=it
                        },
                        placeholder = { Text("Description") },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        enabled = if (bitmap!=null) true else false,
                    )
                }
            }

        }
    }
}