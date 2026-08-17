package com.example.data.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.request.BalancesRequestDO
import com.example.data.domain.response.BalanceDO
import com.example.data.domain.response.CardDO

interface UserCardInfoRepository {
    suspend fun getCards (userPullRequest : Boolean) : ResultWrapper<List<CardDO>>

    suspend fun getBalance (balancesRequestDO: BalancesRequestDO) : ResultWrapper<List<BalanceDO>>

}