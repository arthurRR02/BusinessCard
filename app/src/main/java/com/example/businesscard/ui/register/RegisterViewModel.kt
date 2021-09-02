package com.example.businesscard.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesscard.data.BusinessCardEntity
import com.example.businesscard.data.BusinessCardRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val businessCardRepository: BusinessCardRepository) : ViewModel() {

    private val mCard = MutableLiveData<BusinessCardEntity>()
    val card: LiveData<BusinessCardEntity> = mCard

    fun save(
        id: Int,
        name: String,
        phone: String,
        email: String,
        company: String,
        color: String,
        textColor: String,
    ) {
        val saveValue = BusinessCardEntity().apply {
            this.id = id
            this.name = name
            this.phone = phone
            this.email = email
            this.color = color
            this.company = company
            this.textColor = textColor
        }
        viewModelScope.launch {
            businessCardRepository.insert(saveValue)
        }
    }

    fun loadCard(id: Int) {
        viewModelScope.launch {
            mCard.value = businessCardRepository.get(id)
        }
    }

}