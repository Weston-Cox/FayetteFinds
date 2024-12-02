package edu.uark.fayettefinds.Repository

import kotlinx.coroutines.flow.Flow

class FayetteFindsRepository(private val businessCardDao: BusinessCardDao) {
    val allBusinessCards: Flow<Map<Int, BusinessCard>> = businessCardDao.getAllBusinessCards()



    suspend fun insert(businessCard: BusinessCard) {
        businessCardDao.insert(businessCard)
    }
    suspend fun delete(businessCard: BusinessCard) {
        businessCardDao.delete(businessCard)
    }
    suspend fun update(businessCard: BusinessCard) {
        businessCardDao.update(businessCard)
    }
}