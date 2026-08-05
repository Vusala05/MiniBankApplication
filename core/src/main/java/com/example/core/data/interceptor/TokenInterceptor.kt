package com.example.core.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
) : Interceptor {

    var accessToken : String= ""

    override fun intercept(chain: Interceptor.Chain): Response {

      val request = chain.request()
       val isSpecificUrl =  request.url.encodedPath.contains("auth/refresh")
        if(isSpecificUrl){
            return chain.proceed(request)
        }
        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION,"Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
    companion object{
        private const val AUTHORIZATION = "Authorization"
    }


}
