package com.alish.geekbank.presentation.models

import android.os.Parcelable
import android.widget.ImageView
import com.alish.geekbank.domain.models.NewsModel
import com.alish.geekbank.presentation.base.IBaseDiffModel

data class NewsModelUI(
    val author: String?,
    val title: String?,
    val description: String?,
    override val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
):IBaseDiffModel

fun NewsModel.toUI() = NewsModelUI(
    author,
    title,
    description,
    url,
    urlToImage,
    publishedAt,
    content
)
