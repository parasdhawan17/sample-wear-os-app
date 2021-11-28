package com.example.myapplication.ui.fixtures

import androidx.lifecycle.*
import com.example.myapplication.datamodels.Fixtures
import com.example.myapplication.retrofit.CricketRepository
import kotlinx.coroutines.launch
import java.util.*

class FixturesActivityViewModel(private val repository: CricketRepository) : ViewModel() {
    private val _fixturesLiveDate = MutableLiveData<List<Fixtures.Result>>()
    val fixturesLiveDate : LiveData<List<Fixtures.Result>>  = _fixturesLiveDate

    init {
        viewModelScope.launch {
            var data = repository.getFixturesByDate(Date()).results
            _fixturesLiveDate.postValue(data)
        }
    }
}

class FixturesActivityViewModelFactory(private val repository: CricketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FixturesActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FixturesActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}