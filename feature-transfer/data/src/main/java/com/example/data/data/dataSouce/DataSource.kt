package com.example.feature_transfer.data.dataSouce

import com.example.core.data.model.BaseResponse
import com.example.data.data.request.CalculateCommissionRequest
import com.example.data.data.request.TransferRequest
import com.example.data.data.response.CommissionPreviewResponse
import com.example.data.data.response.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DataSource {

    @POST("transfers/calculate-commission")
    suspend fun calculateCommission(
        @Body request: CalculateCommissionRequest
    ): Response<BaseResponse<CommissionPreviewResponse>>


    @POST("transfers")
    suspend fun executeTransfer(
        @Body request: TransferRequest
    ): Response<BaseResponse<TransferResponse>>
}