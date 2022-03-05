package com.alish.geekbank.presentation.models

import com.alish.geekbank.presentation.base.BaseDiffUtil1

data class CardListUIModel(
    val image: Int,
    val name: String,
    override val id: Long,
) : BaseDiffUtil1
