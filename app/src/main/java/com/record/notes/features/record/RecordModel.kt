package com.record.notes.features.record

import android.content.Context
import com.record.notes.features.database.sqLite.SQLiteDBHelper

class RecordModel {

    fun getRecordingData(name: String, date: String, work: String, amounts: String, context: Context): Boolean? {
        return SQLiteDBHelper(context).recordCustomerDetails(name, date, work, amounts)
    }
}