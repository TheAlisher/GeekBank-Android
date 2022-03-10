package com.alish.geekbank.presentation.base

interface BaseDiffUtilExchange {
    val conversion_rates: Map<String, Double>
    override fun equals(other: Any?): Boolean
}