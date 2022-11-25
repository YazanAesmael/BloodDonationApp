package com.android.bloodon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.bloodon.ui.theme.BlooDonTheme

class MainActivity : ComponentActivity() {
//    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlooDonTheme {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Navigation()
                    getSplashText()
                }
            }
        }
    }
}