package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.repository.TransferRepository
import com.example.data.domain.request.CalculateCommissionRequestDO
import com.example.data.domain.response.CommissionPreviewResponseDO

import javax.inject.Inject

class CalculateCommissionUseCase @Inject constructor(
    val transferRepository: TransferRepository
) {
    suspend operator fun invoke(calculateCommissionRequestDO: CalculateCommissionRequestDO) : ResultWrapper<CommissionPreviewResponseDO>{
        return transferRepository.calculateCommission(calculateCommissionRequestDO)
    }
}