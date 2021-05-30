package com.biblublab.data.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biblublab.data.homeFeature.HomeDao
import com.biblublab.data.homeFeature.HomeDataBaseObject

@Database(entities = [HomeDataBaseObject::class], version = 1)
abstract class SkeletonDataBase : RoomDatabase() {

    companion object{
        const val DB_NAME = "Skeleton_data_base"
    }

    abstract fun homeDao() : HomeDao
}