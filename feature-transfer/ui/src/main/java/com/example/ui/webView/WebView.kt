package com.example.ui.webView

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    threeDSUrl : String?,
    onResult : (String?, String?) -> Unit
){
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object: WebViewClient(){
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {

                        val url = request?.url.toString()

                        return if(url.startsWith("ubank://3ds-callback")){
                            val uri = request?.url
                            val transactionId = uri?.getQueryParameter("transactionId")
                            val status = uri?.getQueryParameter("status")
                            onResult(transactionId,status)
                            true
                        } else{
                            false
                        }
                    }
                }
                threeDSUrl?.let {
                    loadUrl(threeDSUrl)
                }
            }
        }
    )
}