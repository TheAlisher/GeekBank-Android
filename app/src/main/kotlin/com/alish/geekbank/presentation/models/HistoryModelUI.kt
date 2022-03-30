package com.alish.geekbank.presentation.models

import com.alish.geekbank.domain.models.firestore.HistoryModel
import com.alish.geekbank.presentation.base.BaseDiffUtil1

data class HistoryModelUI (
    override val fromCard: String? = null,
    val toCard: String? = null,
    val money: Int? = null
): BaseDiffUtil1

fun HistoryModel.toUI() = HistoryModelUI(
    fromCard, toCard, money
)