package com.example.myapplication.ui.fixtures

import androidx.lifecycle.*
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.retrofit.CricketRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivityViewModel(private val repository: CricketRepository) : ViewModel() {
    private val _fixturesLiveDate = MutableLiveData<Fixtures>()
    val fixturesLiveDate : LiveData<Fixtures>  = _fixturesLiveDate

    init {
        viewModelScope.launch {
            _fixturesLiveDate.postValue(repository.getFixturesByDate(Date()))
        }
    }
}

class MainActivityViewModelFactory(private val repository: CricketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}