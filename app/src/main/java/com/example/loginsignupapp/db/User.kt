package com.example.loginsignupapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")


class User(

    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @PrimaryKey @ColumnInfo(name = "emailId") val emailId: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "mobileNumber") val mobileNumber: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double
) {
    override fun toString(): String {
        return "User(firstName='$firstName', lastName='$lastName', emailId='$emailId', password='$password', mobileNumber='$mobileNumber', longitude=$longitude, latitude=$latitude)"
    }
}