package com.alish.geekbank.domain.models.firestore

data class HistoryModel (
    val fromCard: String? = null,
    val toCard: String? = null,
    val money: Int? = null
        )