package com.example.instagramclonecompose

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.instagramclonecompose.auth.LoginScreen
import com.example.instagramclonecompose.auth.RegisterScreen
import com.example.instagramclonecompose.ui.theme.InstagramCloneComposeTheme
import com.example.instagramclonecompose.uiux.AccountScreen
import com.example.instagramclonecompose.uiux.AddScreen
import com.example.instagramclonecompose.uiux.BottomNavigation
import com.example.instagramclonecompose.uiux.ChatScreen
import com.example.instagramclonecompose.uiux.EditProfileScreen
import com.example.instagramclonecompose.uiux.FollowScreen
import com.example.instagramclonecompose.uiux.FypScreen
import com.example.instagramclonecompose.uiux.HomeScreen
import com.example.instagramclonecompose.uiux.SearchCard
import com.example.instagramclonecompose.uiux.SearchScreen
import com.example.instagramclonecompose.uiux.UserScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramCloneComposeTheme {
                val navController= rememberNavController()
                val context= LocalContext.current
                val isLogin= isLoggedIn(context)
                Scaffold(
//                    bottomBar = { BottomNavigation(navController) }
                ) {paddingValues->
                    NavHost(navController, startDestination = if (isLogin) "Home" else "Login"
                        , modifier = Modifier.padding(paddingValues)){
                        composable("Home"){
                            HomeScreen(navController, paddingValues = paddingValues)
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
                        composable("User"){
                            UserScreen(navController)
                        }
                        composable("Chat/{idSender}",
                            arguments = listOf(navArgument("idSender",{
                                type=NavType.StringType
                            }))
                        ){
                            val idSender=it.arguments?.getString("idSender")
                            if (idSender != null) {
                                ChatScreen(navController,idSender)
                            }
                        }
                        composable("EditProfile"){
                            EditProfileScreen(navController)
                        }
                        composable("Login"){
                            LoginScreen(navController)
                        }
                        composable("Register"){
                            RegisterScreen(navController)
                        }
                        composable("Follow/{userId}",
                            arguments = listOf(navArgument("userId",{
                                type=NavType.StringType
                            }))
                        ){
                            val userId=it.arguments?.getString("userId")
                            if (userId != null) {
                                FollowScreen(navController,userId)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun isLoggedIn(context: Context):Boolean{
    val sharedPreferences=context.getSharedPreferences("Login_Pref",Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isLogin",false)
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