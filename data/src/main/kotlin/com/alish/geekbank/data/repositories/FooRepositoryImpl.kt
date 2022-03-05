//package com.alish.geekbank.data.repositories
//
//import com.alish.geekbank.data.repositories.base.BaseRepository
//import com.alish.geekbank.data.remote.apiservices.FooApiService
//import com.alish.geekbank.data.remote.dtos.toDomain
//import com.alish.geekbank.data.remote.pagingsources.FooPagingSource
//import com.alish.geekbank.domain.repositories.FooRepository
//import javax.inject.Inject
//
//class FooRepositoryImpl @Inject constructor(
//    private val service: FooApiService
//) : BaseRepository(), FooRepository {
//
//    override fun fetchFoo() = doRequest {
//        service.fetchFoo().toDomain()
//    }
//
//    fun fetchFooPaging() = doPagingRequest(FooPagingSource(service))
//}