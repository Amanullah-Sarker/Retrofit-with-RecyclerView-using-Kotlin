package com.amanullah.retrofitwithrecyclerviewusingkotlin

import com.amanullah.retrofitwithrecyclerviewusingkotlin.model.Todos
import retrofit2.Response
import retrofit2.http.GET

interface TodosAPI {

    @GET("/todos")
    suspend fun getTodos(): Response<List<Todos>>
}