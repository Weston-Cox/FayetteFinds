package edu.uark.fayettefinds.ListBusinessCards

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.uark.fayettefinds.Repository.BusinessCard
import edu.uark.fayettefinds.Repository.FayetteFindsRepository

class ListBusinessCardsViewModel(private val repository: FayetteFindsRepository): ViewModel() {

    val allBusinessCards: LiveData<Map<Int, BusinessCard>> = repository.allBusinessCards.asLiveData()

    class ListBusinessCardsViewModelFactory(private val repository: FayetteFindsRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(ListBusinessCardsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ListBusinessCardsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}