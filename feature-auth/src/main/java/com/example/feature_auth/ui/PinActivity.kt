package com.example.feature_auth.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.core.domain.feature.PinFlowChannel
import com.example.feature_auth.ui.presentation.AuthContract
import com.example.feature_auth.ui.presentation.AuthViewModel
import com.example.feature_auth.ui.presentation.PinScreen
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.core_ui.theme.MiniBankAppTheme

@AndroidEntryPoint
class PinActivity : ComponentActivity() {

    @Inject
    lateinit var pinFlowChannel: PinFlowChannel

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiniBankAppTheme() {
                val state by viewModel.state.collectAsStateWithLifecycle()

                PinScreen(
                    state = state,
                    handleIntent = viewModel::handleIntent
                )
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when(effect){
                    is AuthContract.Effect.PinFlowCompleted -> {
                        finish()
                    }
                    is AuthContract.Effect.ShowMessage -> {
                        Toast.makeText(this@PinActivity,effect.message, Toast.LENGTH_LONG).show()

                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            lifecycleScope.launch {
                pinFlowChannel.sendPinResult(success = false)
            }
        }
    }
}