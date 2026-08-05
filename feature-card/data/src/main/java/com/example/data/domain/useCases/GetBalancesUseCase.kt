package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.repository.UserCardInfoRepository
import com.example.data.domain.request.BalancesRequestDO
import com.example.data.domain.response.BalanceDO

import javax.inject.Inject

class GetBalancesUseCase @Inject constructor(
    val cardInfoRepository: UserCardInfoRepository
) {
    suspend operator fun invoke(balancesRequestDO: BalancesRequestDO) : ResultWrapper<List<BalanceDO>>{
        return cardInfoRepository.getBalance(balancesRequestDO)
    }
}