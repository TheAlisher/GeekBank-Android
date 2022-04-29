package com.alish.geekbank.presentation.models


import com.alish.geekbank.domain.models.firestore.CardModel
import com.alish.geekbank.presentation.base.BaseDiffUtilCard

data class CardModelUI(
    val blocked: Boolean? = null,
    val money: Int? = null,
    val date: String? = null,
    override val cardNumber: String? = null,
    val fullName: String? = null
) : BaseDiffUtilCard

fun CardModel.toUI() = CardModelUI(
    blocked, money, date, cardNumber, fullName
)

fun CardModel.toCardUI() = CardsModels.CardUI(
    blocked, money, date, cardNumber, fullName
)
