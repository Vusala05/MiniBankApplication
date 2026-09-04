package com.example.data.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.request.CalculateCommissionRequestDO
import com.example.data.domain.request.TransferRequestDO
import com.example.data.domain.response.CommissionPreviewResponseDO
import com.example.data.domain.response.TransferResponseDO

interface TransferRepository {

   suspend fun calculateCommission( calculateCommissionRequestDO: CalculateCommissionRequestDO) : ResultWrapper<CommissionPreviewResponseDO>
   suspend fun executeTransfer(transferRequestDo : TransferRequestDO) : ResultWrapper<TransferResponseDO>

}