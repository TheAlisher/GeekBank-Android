package com.alish.geekbank.domain.models

data class UsersModel(
    val id: String? = null,
    val password: String? = null,
    val firstCard: Map<String,Any>? = null,
    val secondCard: Map<String,Any>? = null
)