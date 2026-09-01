package com.example.feature_auth.ui.presentation.pinVerified

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.feature.PinFlowChannel
import com.example.core_ui.theme.MiniBankAppTheme
import com.example.feature_auth.ui.presentation.AuthContract
import com.example.feature_auth.ui.presentation.AuthContract.IS_FROM_SESSION_AUTH
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifiedPinActivity : ComponentActivity() {

    @Inject
    lateinit var pinFlowChannel: PinFlowChannel

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: PinVerifiedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromSessionAuth = intent.getBooleanExtra(IS_FROM_SESSION_AUTH,false)

        setContent {
            MiniBankAppTheme() {
                val state by viewModel.state.collectAsStateWithLifecycle()

                PinVerifiedScreen(
                    state = state,
                    handleIntent = viewModel::handleIntent
                )
            }

            BackHandler {
                // Disable back press
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when(effect){
                    is AuthContract.Effect.PinFlowCompleted -> {
                        if(!isFromSessionAuth){
                            navigator.navigate(Route.NavigateDeeplinkRoute(DeeplinkNavigator.UserProfile))
                        }
                        finish()
                    }
                    is AuthContract.Effect.ShowMessage -> {
                        Toast.makeText(this@VerifiedPinActivity,effect.message, Toast.LENGTH_LONG).show()

                    }

                }
            }
        }
    }

}