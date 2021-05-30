package com.biblublab.data.homeFeature

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HomeDao {
    @Query("SELECT * from home_table")
    suspend fun getAllNews(): List<HomeDataBaseObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsDataBaseObject: HomeDataBaseObject)

    @Query("DELETE FROM home_table")
    suspend fun deleteAll()
}