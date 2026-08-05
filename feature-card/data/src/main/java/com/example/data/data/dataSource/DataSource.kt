package com.example.feature_card.data.dataSource

import com.example.core.data.model.BaseResponse
import com.example.feature_card.data.request.BalancesRequest
import com.example.feature_card.data.response.Balance
import com.example.feature_card.data.response.Card
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DataSource {
    @GET("cards")
    suspend fun getCards(): Response<BaseResponse<List<Card>>>


    @POST("balances")
    suspend fun getBalances(
        @Body request: BalancesRequest
    ): Response<BaseResponse<List<Balance>>>
}