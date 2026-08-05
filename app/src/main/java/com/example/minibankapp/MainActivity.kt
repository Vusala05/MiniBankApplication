package com.example.minibankapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.core.data.network.ApiErrorHandler
import com.example.feature_auth.ui.presentation.AuthScreen
import com.example.minibankapp.navigator.MainRoutes
import com.example.minibankapp.navigator.RetainedNavigator
import com.example.minibankapp.ui.theme.MiniBankAppTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiErrorHandler : ApiErrorHandler

    @Inject
    lateinit var retainedNavigator: RetainedNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MiniBankAppTheme {
                GlobalErrorObserver(
                    apiErrorHandler = apiErrorHandler
                )
                MainRoutes(
                    navController = navController,
                    retainedNavigator = retainedNavigator
                )
                Log.e("inside init", "inside init")



            }
        }
    }
}

