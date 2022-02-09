package com.alish.geekbank.data.remote.dtos

data class FooPagingResponse<T>(
    val prev: Int?,
    val next: Int?,
    val data: MutableList<T>
)