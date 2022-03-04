package com.alish.geekbank.presentation.models

import com.alish.geekbank.domain.models.UsersModel

data class UsersModelUI(
    val id: String? = null,
    val password: String? = null,
)

fun UsersModel.toUsersModelUI(): UsersModelUI =
    UsersModelUI(id, password)