package com.codeoflegends.unimarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.ui.theme.UnimarketTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnimarketTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    NavHost(
        navController = rememberNavController(),
        startDestination = "login"
    ) {
        composable("login") {
            Text("Login")
        }
    }
}