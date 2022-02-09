package com.alish.geekbank.data.remote.dtos

import com.alish.geekbank.domain.models.Foo
import com.google.gson.annotations.SerializedName

class FooDto(

    @SerializedName("id")
    val id: Long,

    @SerializedName("bar")
    val bar: String
)

fun FooDto.toDomain() = Foo(id, bar)