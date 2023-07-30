package com.record.notes.features.record

import android.content.Context
import com.record.notes.features.database.DatabaseHelper

class RecordModel {

    fun getRecordingData(
        name: String,
        date: String,
        work: String,
        amounts: String,
        context: Context
    ): Boolean {
        val databaseHelper = DatabaseHelper(context)
        return true
//        return databaseHelper.getLoginUsers(userName, userPassword)
    }
}