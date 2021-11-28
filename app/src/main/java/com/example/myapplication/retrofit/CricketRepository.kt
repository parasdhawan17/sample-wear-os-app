package com.example.myapplication.retrofit

import com.example.myapplication.convertDateToFormat
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.datamodels.MatchDetails
import java.util.*

class CricketRepository(private val apiService: ApiService) {
    suspend fun getFixturesByDate(date: Date) : Fixtures {
        return apiService.getFixturesByDate(convertDateToFormat(date,APIConstants.DATE_FORMAT))
    }
    suspend fun getMatchDetailsById(id: Int) : MatchDetails {
        return apiService.getMatchDetailsById(id)
    }
}