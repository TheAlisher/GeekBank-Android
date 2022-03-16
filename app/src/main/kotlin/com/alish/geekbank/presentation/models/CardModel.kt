package com.alish.geekbank.presentation.models

import com.alish.geekbank.presentation.base.BaseDiffUtilCard

data class CardModel(
    val cardNumber: String? = null,
    override val name: String? = null,
    val date: String? = null,
    val cardMoney: Any? = null,
) : BaseDiffUtilCard
