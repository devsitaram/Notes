package com.record.notes.features.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    private val homeModel = HomeModel()

    // get the customer details from database
    fun getCustomer(context: Context, onCustomerList: (List<CustomerPojo>) -> Unit) {
        val customerList = homeModel.getCustomer(context)
        if (customerList.isNotEmpty()) {
            // add the list
            onCustomerList.invoke(customerList)
        }
    }

    // get customer id for delete
    fun getCustomerId(customerId: String, context: Context): Boolean {
        return if (customerId.isEmpty()){
            Toast.makeText(context, "The id is not available", Toast.LENGTH_SHORT).show()
            false
        } else {
            return setDeleteCustomer(customerId, context)
        }
    }

    // set delete
    private fun setDeleteCustomer(customerId: String, context: Context): Boolean{
        val isSuccess = homeModel.getDeleteCustomer(customerId, context)
        return if (isSuccess==true){
            Toast.makeText(context, "Delete successful.", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Data cannot be delete!", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}