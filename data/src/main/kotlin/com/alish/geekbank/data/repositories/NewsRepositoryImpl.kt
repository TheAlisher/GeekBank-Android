package com.alish.geekbank.data.repositories

import com.alish.geekbank.common.resource.Resource
import com.alish.geekbank.data.repositories.base.BaseRepository
import com.alish.geekbank.data.remote.apiservices.NewsApiService
import com.alish.geekbank.data.remote.dtos.toDomain
import com.alish.geekbank.domain.models.NewsModel
import com.alish.geekbank.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val service: NewsApiService
) : BaseRepository(), NewsRepository {

    override fun fetchNewsDollar() = doRequest {
        service.fetchNews("dollar").articles.map { it.toDomain() }
    }

    override fun fetchNewsBank() = doRequest {
        service.fetchNews("bank").articles.map{ it.toDomain()
        }
    }

    override fun fetchNewsBitcoin() = doRequest {
        service.fetchNews("bitcoin").articles.map { it.toDomain() }
    }
}