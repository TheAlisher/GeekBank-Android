package com.alish.geekbank.presentation.models

import com.alish.geekbank.domain.models.firestore.HistoryModel
import com.alish.geekbank.presentation.base.BaseDiffUtil1

data class HistoryModelUI (
    val dateTime: String? = null,
    override val fromCard: String? = null,
    val toCard: String? = null,
    val condition: String? = null,
    val money: Int? = null
): BaseDiffUtil1

fun HistoryModel.toUI() = HistoryModelUI(
    dateTime,fromCard, toCard,condition, money
)