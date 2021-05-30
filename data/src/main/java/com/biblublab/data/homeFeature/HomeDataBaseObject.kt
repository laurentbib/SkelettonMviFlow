package com.biblublab.data.homeFeature

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_table")
data class HomeDataBaseObject(
    val title: String,
                       val description: String,
                       val url: String,
                       val urlImage: String,
                       val timeStamp: String
) {
    @PrimaryKey
    var id: String = "$title - $timeStamp"

}
