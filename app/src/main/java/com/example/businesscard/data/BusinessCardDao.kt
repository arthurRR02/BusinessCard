package com.example.businesscard.data

import androidx.room.*

@Dao
interface BusinessCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(businessCard: BusinessCardEntity)

    @Query("SELECT * FROM BusinessCardEntity")
    suspend fun getAll(): List<BusinessCardEntity>

    @Query("SELECT * FROM BusinessCardEntity WHERE id = :id")
    suspend fun get(id: Int) : BusinessCardEntity

    @Delete
    suspend fun delete(businessCard: BusinessCardEntity)
}