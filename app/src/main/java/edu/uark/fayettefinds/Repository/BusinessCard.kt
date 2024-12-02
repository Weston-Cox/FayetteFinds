package edu.uark.fayettefinds.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesscards_table")
data class BusinessCard(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name="businessName") var businessName:String,
    @ColumnInfo(name="typeOfBusiness") var typeOfBusiness:String,
    @ColumnInfo(name="title") var title:String,
    @ColumnInfo(name="content") var content:String,
    @ColumnInfo(name="phone") var phone:String,
    @ColumnInfo(name="email") var email:String,
    @ColumnInfo(name="website") var website:String,
    @ColumnInfo(name="address") var address:String

)
