package com.example.instagramclonecompose.uiux

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.instagramclonecompose.model.Bottom

@Composable
fun BottomNavigation(navController: NavController,modifier: Modifier = Modifier) {
    val bottomItem= listOf(
        Bottom("Home", Icons.Default.Home,"Home"),
        Bottom("Search",Icons.Default.Search,"Search"),
        Bottom("Add",Icons.Default.AddCircle,"Add"),
        Bottom("Fyp", Icons.Default.Info,"Fyp"),
        Bottom("Account",Icons.Default.Person,"Account")
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute=navBackStackEntry?.destination?.route

    NavigationBar {
        bottomItem.forEachIndexed { index, bottom ->
            NavigationBarItem(
                selected = currentRoute==bottom.route,
                onClick = {
                    navController.navigate(bottom.route){
                        restoreState=true
                        launchSingleTop=true
                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }
                    }
                },
                icon = {
                    Icon(bottom.icon,null)
                },
                label = {
                    Text(bottom.label)
                }
            )
        }
    }
}