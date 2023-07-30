package com.record.notes.features.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val homeModel = HomeModel()

    fun getCustomer(context: Context, onCustomerList: (List<CustomerPojo>) -> Unit) {
        val customerList = homeModel.getCustomer(context)
        if (customerList.isNotEmpty()) {
            // add the list
            onCustomerList.invoke(customerList)
//            Toast.makeText(context, "Successful.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Unsuccessful.", Toast.LENGTH_SHORT).show()
        }
    }
}