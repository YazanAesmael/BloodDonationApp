package com.android.bloodon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String){
    object Home : BottomNavItem("Home", Icons.Outlined.Home,"mainBloodScreen_screen")
    object Account: BottomNavItem("My Account", Icons.Outlined.AccountCircle,"profile_screen")
}