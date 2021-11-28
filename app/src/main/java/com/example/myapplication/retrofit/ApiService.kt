package com.example.myapplication.retrofit

import com.example.myapplication.BuildConfig
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.datamodels.MatchDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @GET(APIConstants.FIXETURES_BY_DATE + "/{date}")
    @Headers(
        "x-rapidapi-host:${APIConstants.HOST}",
        "x-rapidapi-key:${BuildConfig.API_KEY}"
    )
    suspend fun getFixturesByDate(@Path(value = "date") date: String): Fixtures


    @GET(APIConstants.MATCH + "/{id}")
    @Headers(
        "x-rapidapi-host:${APIConstants.HOST}",
        "x-rapidapi-key:${BuildConfig.API_KEY}"
    )
    suspend fun getMatchDetailsById(@Path(value = "id") id: Int): MatchDetails
}