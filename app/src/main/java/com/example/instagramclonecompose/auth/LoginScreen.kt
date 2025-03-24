package com.example.instagramclonecompose.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.instagramclonecompose.R
import com.example.instagramclonecompose.ui.theme.InstagramCloneComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun LoginScreen(navController: NavController,modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hasErrorEmail by remember { mutableStateOf(false) }
    var hasErrorPassword by remember { mutableStateOf(false) }
    val focusRequesterEmail by remember { mutableStateOf(FocusRequester()) }
    val focusRequesterPassword by remember { mutableStateOf(FocusRequester()) }
    val context= LocalContext.current
    val keyboardController= LocalSoftwareKeyboardController.current

    Column(modifier
        .paint(painter = painterResource(R.drawable.img), contentScale = ContentScale.FillBounds)
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(modifier
            .padding(8.dp)
            .paint(
                painter = painterResource(R.drawable.img),
                contentScale = ContentScale.FillBounds
            )
            .height(200.dp)
            .width(280.dp)
        )
        Card(modifier
            .paint(
                painter = painterResource(R.drawable.img_1),
                contentScale = ContentScale.FillBounds
            )
            .height(300.dp)
            .width(280.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email=it
                    hasErrorEmail=false
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email,null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequesterEmail)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password=it
                    hasErrorPassword=false
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock,null) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequesterPassword),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                if (email.isEmpty()) {
                    hasErrorEmail=true
                }
                else if (password.isEmpty()){
                    hasErrorPassword=true
                }
                else{
                    val firebaseAuth=FirebaseAuth.getInstance()
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(context,"Login Succesfully",Toast.LENGTH_SHORT).show()
                            saveLogin(context,true)
                            navController.navigate("Home"){
                                popUpTo(navController.graph.startDestinationId){
                                    inclusive=true
                                }
                            }
                        }
                        else{
                            Toast.makeText(context,"Login Failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },modifier
                .fillMaxWidth()
                .padding(8.dp), shape = RoundedCornerShape(0.dp)) {
                Text("Login", fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = {navController.navigate("Register")}) {
                Text("Dont Have an Account?")
            }
        }

        if (hasErrorEmail){
            LaunchedEffect(Unit) {
                focusRequesterEmail.requestFocus()
            }
            Toast.makeText(context,"Email is Empty",Toast.LENGTH_SHORT).show()

        }
        else if (hasErrorPassword){
            LaunchedEffect(Unit) {
                focusRequesterPassword.requestFocus()
            }
            Toast.makeText(context,"Password is Empty",Toast.LENGTH_SHORT).show()
        }
    }
}

fun saveLogin(context: Context,isLoggedIn:Boolean){
    val sharedPreferences=context.getSharedPreferences("Login_Pref",Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    editor.putBoolean("isLogin",isLoggedIn)
    editor.apply()
}

@Preview
@Composable
private fun LoginPrev() {
    InstagramCloneComposeTheme {
        LoginScreen(navController = rememberNavController())
    }
}