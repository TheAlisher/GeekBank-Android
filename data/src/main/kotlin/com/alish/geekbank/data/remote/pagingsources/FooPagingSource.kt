//package com.alish.geekbank.data.remote.pagingsources
//
//import com.alish.geekbank.data.remote.apiservices.NewsApiService
//import com.alish.geekbank.data.remote.dtos.FooDto
//import com.alish.geekbank.data.remote.pagingsources.base.BasePagingSource
//import com.alish.geekbank.domain.models.Foo
//
//class FooPagingSource(
//    private val service: NewsApiService
//) : BasePagingSource<FooDto, Foo>(
//    { service.fetchFooPaging(it) },
//    { data -> data.map { it.toDomain() } }
//)