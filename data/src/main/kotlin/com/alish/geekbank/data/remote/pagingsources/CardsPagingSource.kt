//package com.alish.geekbank.data.remote.pagingsources
//
//import com.alish.geekbank.data.remote.apiservices.FooApiService
//import com.alish.geekbank.data.remote.dtos.FooDto
//import com.alish.geekbank.data.remote.dtos.toDomain
//import com.alish.geekbank.data.remote.pagingsources.base.BasePagingSource
//import com.alish.geekbank.domain.models.Foo
//
//class FooPagingSource(
//    private val service: FooApiService
//) : BasePagingSource<FooDto, Foo>(
//    { service.fetchFooPaging(it) },
//    { data -> data.map { it.toDomain() } }
//)