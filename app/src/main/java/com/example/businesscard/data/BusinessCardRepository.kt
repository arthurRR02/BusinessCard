package com.example.businesscard.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BusinessCardRepository(private val businessCardDao: BusinessCardDao) {

    suspend fun insert(businessCard: BusinessCardEntity) {
        runBlocking {
            launch(Dispatchers.IO) {
                businessCardDao.insert(businessCard)
            }
        }
    }

    suspend fun getAll(): List<BusinessCardEntity> {
        return businessCardDao.getAll()
    }

    suspend fun get(id: Int): BusinessCardEntity {
        return businessCardDao.get(id)
    }

    suspend fun delete(businessCard: BusinessCardEntity) {
        return businessCardDao.delete(businessCard)
    }

}