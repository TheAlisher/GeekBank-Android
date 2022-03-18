package com.alish.geekbank.presentation.models

import com.alish.geekbank.domain.models.UsersModel
import com.alish.geekbank.presentation.base.BaseDiffUtilCard

data class UsersModelUI(
    val id: String? = null,
    val password: String? = null,
    val fullName: String? = null
)

fun UsersModel.toUsersModelUI(): UsersModelUI =
    UsersModelUI(id, password,fullName)