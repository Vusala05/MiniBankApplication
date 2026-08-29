package com.example.minibankapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.core.data.network.ApiErrorHandler
import com.example.core.domain.feature.PinFlowChannel
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.minibankapp.navigator.MainRoutes
import com.example.minibankapp.navigator.RetainedNavigator
import com.example.core_ui.theme.MiniBankAppTheme
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiErrorHandler : ApiErrorHandler
    @Inject
    lateinit var handleErrorUseCase: HandleErrorUseCase
    @Inject
    lateinit var retainedNavigator: RetainedNavigator

    @Inject
    lateinit var pinFlowChannel: PinFlowChannel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MiniBankAppTheme {
                GlobalErrorObserver(
                    apiErrorHandler = apiErrorHandler,
                    handleErrorUseCase = handleErrorUseCase
                )
                MainRoutes(
                    navController = navController,
                    retainedNavigator = retainedNavigator
                )
                Log.e("inside init", "inside init")

                lifecycleScope.launch {
                    val success = pinFlowChannel.awaitPinResult()
                    if (success) {
                        retainedNavigator.navigate(
                            Route.NavigateDeeplinkRoute(DeeplinkNavigator.UserProfile)
                        )
                    } else {
                        // pin ləğv olundu / uğursuz — istəsən app-ı bağla və ya login-ə yönləndir
                    }
                }

            }
        }
    }
}

