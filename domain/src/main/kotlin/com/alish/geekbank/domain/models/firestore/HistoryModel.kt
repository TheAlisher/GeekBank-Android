package com.alish.geekbank.domain.models.firestore

data class HistoryModel (
    val dateTime: String? = null,
    val fromCard: String? = null,
    val toCard: String? = null,
    val condition: String? = null,
    val money: Int? = null
        )