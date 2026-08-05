package com.example.feature_transaction.data.dataSource

import com.example.core.data.model.BaseResponse
import com.example.feature_transaction.data.response.Transaction
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataSource {

    @GET("transactions")
    suspend fun getTransactions(
        @Query("limit") limit: Int = 20,
        @Query(/* value = */ "offset") offset: Int = 0
    ): Response<BaseResponse<List<Transaction>>>
}