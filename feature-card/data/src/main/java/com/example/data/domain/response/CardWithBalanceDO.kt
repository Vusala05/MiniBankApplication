package com.example.data.domain.response

import com.example.core.domain.model.ResultWrapper

data class CardWithBalanceDO(
    val cardDO: CardDO,
    val balanceDO: ResultWrapper<BalanceDO>? = null
) {
}