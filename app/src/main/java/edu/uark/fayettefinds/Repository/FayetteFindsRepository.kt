package edu.uark.fayettefinds.Repository

import kotlinx.coroutines.flow.Flow

class FayetteFindsRepository(private val businessCardDao: BusinessCardDao) {
    val allBusinessCards: Flow<Map<Long, BusinessCard>> = businessCardDao.getBusinessCards()

    suspend fun insert(businessCard: BusinessCard) {
        businessCardDao.insert(businessCard)
    }
}