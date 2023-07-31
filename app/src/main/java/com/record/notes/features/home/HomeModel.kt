package com.record.notes.features.home

import android.content.Context
import com.record.notes.features.database.DatabaseHelper

class HomeModel {
    fun getCustomer(context: Context): ArrayList<CustomerPojo> {
        return DatabaseHelper(context).getCustomerData(context)
    }

    fun getDeleteCustomer(customerId: String, context: Context): Boolean? {
        return DatabaseHelper(context).deleteCustomerById(customerId)
    }
}