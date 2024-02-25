package com.hyeok.blindpharmacy.ui.manage

import androidx.room.Entity
import com.hyeok.blindpharmacy.Drug

@Entity(tableName = "drugLog")
data class DrugsLog(
    var id: Long? =null,
    val drug: Drug,
    val timeStamp: Long
)
