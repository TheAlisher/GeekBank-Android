package com.alish.geekbank.presentation.models.exchange

import com.alish.geekbank.presentation.base.BaseDiffUtilExchange

data class ExchangeModelsUI(
    override val exchangeName: String,
    val exchange: String,
): BaseDiffUtilExchange