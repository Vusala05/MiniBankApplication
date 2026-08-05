package com.example.feature_transfer.data.dataSouce

import com.example.core.data.model.BaseResponse
import com.example.feature_transfer.data.request.CalculateCommissionRequest
import com.example.feature_transfer.data.request.TransferRequest
import com.example.feature_transfer.data.response.CommissionPreviewResponse
import com.example.feature_transfer.data.response.TransferResponse
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