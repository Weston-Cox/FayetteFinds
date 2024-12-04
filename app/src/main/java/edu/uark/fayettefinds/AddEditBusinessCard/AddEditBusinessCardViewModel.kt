package edu.uark.fayettefinds.AddEditBusinessCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.uark.fayettefinds.Repository.BusinessCard
import edu.uark.fayettefinds.Repository.FayetteFindsRepository
import kotlinx.coroutines.launch

class AddEditBusinessCardViewModel(private val repository: FayetteFindsRepository): ViewModel() {
    val _businessCard = MutableLiveData<BusinessCard>().apply {value=null}
    val businessCard:LiveData<BusinessCard>
        get() = _businessCard

    fun start(itemId:Long)
    {
        viewModelScope.launch {
            repository.allBusinessCards.collect{
                _businessCard.value = it[itemId]
            }
        }
    }

    fun insert(businessCard:BusinessCard)
    {
        viewModelScope.launch {
            repository.insert(businessCard)
        }
    }

    class AddEditBusinessCardViewModelFactory(private val repository: FayetteFindsRepository) : ViewModelProvider.Factory{

        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(AddEditBusinessCardViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return AddEditBusinessCardViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}