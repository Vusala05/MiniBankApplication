package com.example.ui.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CardInfoRoute(){
   val  viewModel : CardInfoViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()
    CardInfoScreen(
        state = state,
        handleIntent = viewModel::handleIntent
    )

    val context = LocalContext.current
    LaunchedEffect(Unit){
        viewModel.effect.collectLatest {
            when(it){
                is CardInfoContract.Effect.ShowErrorMessage -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}