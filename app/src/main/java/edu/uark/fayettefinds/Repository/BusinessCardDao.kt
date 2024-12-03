package edu.uark.fayettefinds.Repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessCardDao {

    @MapInfo(keyColumn = "id")
    @Query("SELECT * FROM businesscards_table order by id ASC")
    fun getBusinessCards(): Flow<Map<Long, BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)

    @Query("DELETE FROM businesscards_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM businesscards_table")
    suspend fun getAllBusinessCards(): List<BusinessCard>

    @Update
    suspend fun updateItem(businessCard: BusinessCard)
}