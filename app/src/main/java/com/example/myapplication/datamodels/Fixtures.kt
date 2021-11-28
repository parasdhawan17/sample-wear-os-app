package com.example.myapplication.datamodels

import java.io.Serializable

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
    ) : Serializable {
        data class Away(
            val code: String,
            val id: Int,
            val name: String
        ) : Serializable

        data class Home(
            val code: String,
            val id: Int,
            val name: String
        ) : Serializable
    }
}