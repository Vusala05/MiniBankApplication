package com.example.feature_transfer.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.feature_transfer.domain.request.CalculateCommissionRequestDO
import com.example.feature_transfer.domain.request.TransferRequestDO
import com.example.feature_transfer.domain.response.CommissionPreviewResponseDO
import com.example.feature_transfer.domain.response.TransferResponseDO

interface TransferRepository {

   suspend fun calculateCommission( calculateCommissionRequestDO: CalculateCommissionRequestDO) : ResultWrapper<CommissionPreviewResponseDO>
   suspend fun executeTransfer(transferRequestDo : TransferRequestDO) : ResultWrapper<TransferResponseDO>

}