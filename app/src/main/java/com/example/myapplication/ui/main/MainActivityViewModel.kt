package com.example.myapplication.ui.main

import androidx.lifecycle.*
import com.example.myapplication.Constants
import com.example.myapplication.datamodels.MatchDetails
import com.example.myapplication.datastore.DataStore
import com.example.myapplication.retrofit.CricketRepository
import com.example.myapplication.retrofit.Result
import com.example.myapplication.ui.fixtures.FixturesActivityViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: CricketRepository,private val dataStore: DataStore) : ViewModel() {

    private val matchId = MutableLiveData<Int>()
    val matchDetails : LiveData<Result<MatchDetails>> = Transformations.switchMap(matchId){
        liveData {
            emit(Result.Loading(true))
            emit(Result.Success(repository.getMatchDetailsById(it)))
            emit(Result.Loading(false))
        }
    }

    init {
        val savedMatchId = dataStore.getInt(Constants.SHARED_PREF_KEY_MATCH_ID)
        if(savedMatchId>0) matchId.value = savedMatchId
    }

    fun refresh(){
        matchId.value = matchId.value
    }

    fun setMatchId(id : Int){
        matchId.value = id
        dataStore.saveInt(Constants.SHARED_PREF_KEY_MATCH_ID,id)
    }
}

class MainActivityViewModelFactory(private val repository: CricketRepository,private val dataStore: DataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository,dataStore) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}