package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.repository.TransferRepository
import com.example.data.domain.request.TransferRequestDO
import com.example.data.domain.response.TransferResponseDO
import javax.inject.Inject

class ExecuteTransferUseCase @Inject constructor(
    val transferRepository: TransferRepository
) {
    suspend operator fun invoke(transferRequestDO: TransferRequestDO) : ResultWrapper<TransferResponseDO>{
        return transferRepository.executeTransfer(transferRequestDO)
    }
}