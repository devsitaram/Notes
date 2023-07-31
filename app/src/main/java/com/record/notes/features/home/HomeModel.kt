package com.record.notes.features.home

import android.content.Context
import com.record.notes.features.database.sqLite.SQLiteDBHelper

class HomeModel {
    fun getCustomer(context: Context): ArrayList<CustomerPojo> {
        return SQLiteDBHelper(context).getCustomerData(context)
    }

    fun getDelete(customerId: String, customerName: String, context: Context): Boolean? {
        return SQLiteDBHelper(context).deleteCustomerById(customerId, customerName, context)
    }

    fun getUpdate(id: String, name: String, date: String, work: String, amounts: String, context: Context): Boolean? {
        return  SQLiteDBHelper(context).updateCustomer(id, name, date, work, amounts, context)
    }
}