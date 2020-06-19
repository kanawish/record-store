package com.kanawish.recordstore.model.backend

import com.kanawish.recordstore.model.RecordState
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Simple typing for DI bindings.
 */
typealias BaseUrl = String

/*
@startuml
cloud "HTTP\n" {
    database X [
    ===
    \nRecord Store Server\n
    ]
}
@enduml
 */
interface RecordStoreRestApi {
    @GET("record.json")
    fun getRecord(): Observable<RecordState>

    @PUT("record.json")
    fun putRecord(@Body record: RecordState): Observable<RecordState>
}

@Singleton @ProvidesSingletonInScope
class RecordStoreRestApiProvider @Inject constructor(baseUrl:BaseUrl) : Provider<RecordStoreRestApi> {
    override fun get(): RecordStoreRestApi = retrofit.create(RecordStoreRestApi::class.java)

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}