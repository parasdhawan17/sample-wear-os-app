package com.example.myapplication.retrofit

import com.example.myapplication.datamodels.Fixtures
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @GET(APIConstants.FIXETURES_BY_DATE + "/{date}")
    @Headers(
        "x-rapidapi-host:${APIConstants.HOST}",
        "x-rapidapi-key:${APIConstants.API_KEY}"
    )
    suspend fun getFixturesByDate(@Path(value = "date") date: String): Fixtures
}