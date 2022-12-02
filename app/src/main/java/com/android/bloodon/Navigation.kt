package com.android.bloodon

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("donateNotes_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyNotificationsAround()
            }
        }
        composable("profile_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                ProfileScreenCompose(navController)
            }
        }
        composable("settings_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                SettingsScreen(navController, false)
            }
        }
        composable("mainBloodScreen_screen") {
            MainScreenCompose(navController)
        }
        composable("age_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                AppBarCompose("Blood Donor")
                SignUpScreen(navController = navController)
            }
        }
        composable("login_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                AppBarCompose("Blood Donor")
                LoginScreen(navController = navController)
            }
        }
        composable("phoneNumber_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                AppBarCompose("Blood Donor")
                PhoneNumberScreen(navController = navController)
            }
        }
        composable("authPhone_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                AppBarCompose("Blood Donor")
                AuthenticatePhoneNumberScreen(navController = navController)
            }
        }
        composable("lazyRequestsAround_screen") {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyRequestsAround()
            }
        }
    }
}