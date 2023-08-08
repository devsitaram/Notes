package com.record.notes.features.home

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.record.notes.features.database.sqLite.SQLiteDBHelper

class HomeModel() {
    fun getCustomer(context: Context): ArrayList<CustomerPojo> {
        return SQLiteDBHelper(context).getCustomerData(context)
    }

    fun deleteCustomer(name: String?, context: Context?): Boolean? {
        return SQLiteDBHelper(context).deleteCustomer(name)
//        return SQLiteDBHelper(context).deleteCustomerById(customerId)
    }

    fun getUpdate(id: Long?, name: String?, date: String?, work: String?, amounts: String?, context: Context?): Boolean? {
        return  SQLiteDBHelper(context).updateCustomer(id, name, date, work, amounts, context)
    }
}