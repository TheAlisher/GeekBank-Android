package com.alish.geekbank.domain.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.domain.models.NewsModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun fetchNewsDollar(): Flow<Resource<List<NewsModel>>>
    fun fetchNewsBank(): Flow<Resource<List<NewsModel>>>
    fun fetchNewsBitcoin(): Flow<Resource<List<NewsModel>>>
}