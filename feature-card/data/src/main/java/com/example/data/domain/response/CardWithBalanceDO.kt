package com.example.data.domain.response

data class CardWithBalanceDO(
    val cardDO: CardDO,
    val balanceDO: BalanceDO?
) {
}