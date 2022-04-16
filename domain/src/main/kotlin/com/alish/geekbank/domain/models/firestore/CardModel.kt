package com.alish.geekbank.domain.models.firestore

data class CardModel(
    val blocked: Boolean? = null,
    val money: Int? = null,
    val date: String? = null,
    val cardNumber: String? = null,
    val fullName: String? = null
) {
}