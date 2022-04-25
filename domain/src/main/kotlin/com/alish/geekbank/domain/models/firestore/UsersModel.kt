package com.alish.geekbank.domain.models.firestore

data class UsersModel(
    val id: String? = null,
    val password: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val number: String? = null,
    val condition: String? = null,
    val userImage: String? = null
)

