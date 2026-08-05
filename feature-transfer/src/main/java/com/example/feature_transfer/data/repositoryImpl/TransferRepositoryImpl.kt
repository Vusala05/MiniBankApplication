package com.example.feature_transfer.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.feature_transfer.data.dataSouce.DataSource
import com.example.feature_transfer.data.util.TransactionStatus
import com.example.feature_transfer.domain.repository.TransferRepository
import com.example.feature_transfer.domain.request.CalculateCommissionRequestDO
import com.example.feature_transfer.domain.request.TransferRequestDO
import com.example.feature_transfer.domain.response.CommissionPreviewResponseDO
import com.example.feature_transfer.domain.response.TransferResponseDO
import javax.inject.Inject


class TransferRepositoryImpl @Inject constructor(
    val globalNetwork: GlobalNetwork,
    val dataSource: DataSource
) : TransferRepository {
    override suspend fun calculateCommission(calculateCommissionRequestDO: CalculateCommissionRequestDO): ResultWrapper<CommissionPreviewResponseDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.calculateCommission(calculateCommissionRequestDO.toEntity())
        }){ result ->
            result?.toDomain() ?: CommissionPreviewResponseDO("","","","","")
        }
    }

    override suspend fun executeTransfer(transferRequestDo: TransferRequestDO): ResultWrapper<TransferResponseDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.executeTransfer(transferRequestDo.toEntity())
        }){ result ->
            result?.toDomain() ?: TransferResponseDO("","","","","","","", TransactionStatus.UNKNOWN,"")
        }
    }

}