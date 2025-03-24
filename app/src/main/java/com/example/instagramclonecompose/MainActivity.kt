package com.example.instagramclonecompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramclonecompose.ui.theme.InstagramCloneComposeTheme
import com.example.instagramclonecompose.uiux.AccountScreen
import com.example.instagramclonecompose.uiux.AddScreen
import com.example.instagramclonecompose.uiux.BottomNavigation
import com.example.instagramclonecompose.uiux.EditProfileScreen
import com.example.instagramclonecompose.uiux.FypScreen
import com.example.instagramclonecompose.uiux.HomeScreen
import com.example.instagramclonecompose.uiux.SearchCard
import com.example.instagramclonecompose.uiux.SearchScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramCloneComposeTheme {
                val navController= rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navController) }
                ) {
                    NavHost(navController, startDestination = "Home", modifier = Modifier.padding(it)){
                        composable("Home"){
                            HomeScreen(navController)
                        }
                        composable("Search"){
                            SearchScreen(navController)
                        }
                        composable("Add"){
                            AddScreen(navController)
                        }
                        composable("Fyp"){
                            FypScreen(navController)
                        }
                        composable("Account"){
                            AccountScreen(navController)
                        }
                        composable("Chat"){

                        }
                        composable("EditProfile"){
                            EditProfileScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InstagramCloneComposeTheme {
        Greeting("Android")
    }
}