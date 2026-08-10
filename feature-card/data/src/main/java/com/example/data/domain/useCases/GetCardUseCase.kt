package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.repository.UserCardInfoRepository
import com.example.data.domain.response.CardDO
import javax.inject.Inject

class GetCardUseCase @Inject constructor(
    val cardInfoRepository: UserCardInfoRepository
) {
    suspend operator fun invoke() : ResultWrapper<List<CardDO>>{
        return cardInfoRepository.getCards(userForceToRefresh = false)
    }
}