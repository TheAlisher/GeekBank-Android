package com.alish.geekbank.presentation.models

import com.alish.geekbank.domain.models.Foo
import com.alish.geekbank.presentation.base.IBaseDiffModel

data class CardsUI(
    override val id: Long,
    val bar: String
) : IBaseDiffModel

fun Foo.toUI() = CardsUI(id, bar)