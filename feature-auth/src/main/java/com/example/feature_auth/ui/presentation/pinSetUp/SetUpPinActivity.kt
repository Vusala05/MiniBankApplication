package com.example.feature_auth.ui.presentation.pinSetUp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.core_ui.theme.MiniBankAppTheme
import com.example.feature_auth.ui.presentation.pinVerified.VerifiedPinActivity
import com.example.feature_auth.ui.presentation.AuthContract
import com.example.feature_auth.ui.presentation.pinVerified.PinVerifiedScreen
import com.example.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SetUpPinActivity : ComponentActivity() {

    private val viewModel: PinSetUpViewModel by viewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiniBankAppTheme() {
                val state by viewModel.state.collectAsStateWithLifecycle()

                PinSetUpScreen (
                    state = state,
                    handleIntent = viewModel::handleIntent
                )
            }

        }

        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when(effect){
                    is AuthContract.Effect.PinFlowCompleted -> {
                     val intent = Intent(this@SetUpPinActivity, VerifiedPinActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthContract.Effect.ShowMessage -> {
                        Toast.makeText(this@SetUpPinActivity,effect.message, Toast.LENGTH_LONG).show()

                    }

                }
            }
        }
    }
}