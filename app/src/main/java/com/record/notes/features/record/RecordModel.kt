package com.record.notes.features.record

import android.content.Context
import com.record.notes.features.database.DatabaseHelper

class RecordModel {

    fun getRecordingData(date: String, name: String, work: String, amounts: String, context: Context): Boolean? {
        return DatabaseHelper(context).recordCustomerDetails(date, name, work, amounts)
    }
}