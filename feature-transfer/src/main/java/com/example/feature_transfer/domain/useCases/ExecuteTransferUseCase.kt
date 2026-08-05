package com.example.feature_transfer.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_transfer.domain.repository.TransferRepository
import com.example.feature_transfer.domain.request.TransferRequestDO
import com.example.feature_transfer.domain.response.TransferResponseDO
import javax.inject.Inject

class ExecuteTransferUseCase @Inject constructor(
    val transferRepository: TransferRepository
) {
    suspend operator fun invoke(transferRequestDO: TransferRequestDO) : ResultWrapper<TransferResponseDO>{
        return transferRepository.executeTransfer(transferRequestDO)
    }
}