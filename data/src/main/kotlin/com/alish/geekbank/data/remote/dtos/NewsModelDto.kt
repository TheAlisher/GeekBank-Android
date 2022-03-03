package com.alish.geekbank.data.remote.dtos


import com.alish.geekbank.domain.models.NewsModel
import com.google.gson.annotations.SerializedName

data class NewsModelDto(
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("content") val content: String?
)

fun NewsModelDto.toDomain() = NewsModel(
    author,title,description,url,urlToImage,publishedAt,content
)