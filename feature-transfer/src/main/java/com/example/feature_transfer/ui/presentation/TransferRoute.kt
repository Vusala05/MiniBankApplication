package com.example.feature_transfer.ui.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransferRoute() {
   val viewModel : TransferViewModel = hiltViewModel()
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    TransferScreen(
        state = state,
        handleIntent = viewModel::handleIntent
    )
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when(it){
                is TransferContract.Effect.ShowMessage -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}