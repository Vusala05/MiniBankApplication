package com.example.feature_transfer.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_transfer.domain.repository.TransferRepository
import com.example.feature_transfer.domain.request.CalculateCommissionRequestDO
import com.example.feature_transfer.domain.response.CommissionPreviewResponseDO
import javax.inject.Inject

class CalculateCommissionUseCase @Inject constructor(
    val transferRepository: TransferRepository
) {
    suspend operator fun invoke(calculateCommissionRequestDO: CalculateCommissionRequestDO) : ResultWrapper<CommissionPreviewResponseDO>{
        return transferRepository.calculateCommission(calculateCommissionRequestDO)
    }
}