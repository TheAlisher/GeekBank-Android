package com.alish.geekbank.presentation.models

import com.alish.geekbank.presentation.base.IBaseDiffModel

data class CardsUIModel(
    val image: Int,
    val name: String,
    override val id: Long,
) : IBaseDiffModel
