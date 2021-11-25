package com.example.myapplication.datamodels

data class Fixtures(
    val results: List<Result>
) {
    data class Result(
        val away: Away,
        val date: String,
        val home: Home,
        val id: Int,
        val match_subtitle: String,
        val match_title: String,
        val result: String,
        val series_id: Int,
        val status: String,
        val venue: String
    ) {
        data class Away(
            val code: String,
            val id: Int,
            val name: String
        )

        data class Home(
            val code: String,
            val id: Int,
            val name: String
        )
    }
}