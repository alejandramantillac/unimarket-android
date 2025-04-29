package com.codeoflegends.unimarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.codeoflegends.unimarket.core.navigation.AppNavigation
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.ui.theme.UnimarketTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppContentWrapper

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
    AppContentWrapper {
        AppNavigation(
            NavigationManager(rememberNavController(), hiltViewModel()),
            startDestination = Routes.Home.route,
        )
    }
}