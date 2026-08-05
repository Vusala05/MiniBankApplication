package com.example.minibankapp

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.core.data.network.ApiErrorHandler
import com.example.core_ui.util.BusinessErrorTypeEnum
import com.example.core_ui.util.SystemErrorTypeEnum
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GlobalErrorObserver(
    apiErrorHandler: ApiErrorHandler,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        apiErrorHandler.effect.collectLatest {
            val systemError = SystemErrorTypeEnum.findErrorType(it.code)
             if(systemError!=null) {
                 Toast.makeText(context, systemError.errorMessage, Toast.LENGTH_LONG).show()
             }
            if(it.code == 1003){
                val businessError = BusinessErrorTypeEnum.getBusinessError(it.code)
                Toast.makeText(context,businessError.errorMessage, Toast.LENGTH_LONG).show()

            }

        }
    }
}