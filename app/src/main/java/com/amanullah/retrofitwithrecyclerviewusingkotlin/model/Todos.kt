package com.amanullah.retrofitwithrecyclerviewusingkotlin.model

data class Todos(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)