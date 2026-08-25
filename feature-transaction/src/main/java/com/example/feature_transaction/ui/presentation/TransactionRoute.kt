package com.example.feature_transaction.ui.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransactionRoute(
    cardId : String?=null
){

    val viewModel : TransactionViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is TransactionContract.Effect.ShowErrorMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    LaunchedEffect(cardId) {
        viewModel.handleIntent(TransactionContract.Intent.LoadCardTransactions(cardId))

    }

    TransactionScreen(
        state = state,
        handleIntent = viewModel::handleIntent
    )



}