package com.example.businesscard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BusinessCardEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var phone: String = ""
    var email: String = ""
    var company: String = ""
    var color: String = ""
    var textColor: String = ""
}
