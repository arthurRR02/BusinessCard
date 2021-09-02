package com.example.businesscard.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesscard.data.BusinessCardEntity
import com.example.businesscard.data.BusinessCardRepository
import kotlinx.coroutines.launch

class MainViewModel(private val businessCardRepository: BusinessCardRepository) : ViewModel() {
    private val mBusinessCardList = MutableLiveData<List<BusinessCardEntity>>()
    val businessCardList: LiveData<List<BusinessCardEntity>> = mBusinessCardList

    fun getAll(){
        viewModelScope.launch {
            mBusinessCardList.value = businessCardRepository.getAll()
        }
    }

    fun delete(id: Int){
        viewModelScope.launch {
            val businessCard = businessCardRepository.get(id)
            businessCardRepository.delete(businessCard)
        }
    }
}