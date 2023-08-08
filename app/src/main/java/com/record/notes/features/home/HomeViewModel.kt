package com.record.notes.features.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.record.notes.features.util.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val validator = InputValidator()
    private val homeModel = HomeModel()
    // get the customer details from database
    fun getCustomer(context: Context, onCustomerList: (List<CustomerPojo>) -> Unit) {
        val customerList = homeModel.getCustomer(context)
        if (customerList.isNotEmpty()) {
            // add the list
            onCustomerList.invoke(customerList)
        } else {
            Toast.makeText(context, "Sorry Brow No data!", Toast.LENGTH_SHORT).show()
        }
    }

    // get customer id for delete
    fun getDeleteCustomer(name: String?, context: Context?): Boolean {
        return if (name!!.isEmpty()){
            Toast.makeText(context, "The id is not available", Toast.LENGTH_SHORT).show()
            false
        } else {
            return setDeleteCustomer(name, context)
        }
    }
    // set delete
    private fun setDeleteCustomer(name: String, context: Context?): Boolean {
        val isSuccess = homeModel.deleteCustomer(name, context)
        return if (isSuccess == true){
            Toast.makeText(context, "Delete successful.", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Data cannot be delete!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // update
    fun getUpdateCustomer(id: Long?, name: String?, date: String?, work: String?, amounts: String?, context: Context?): Boolean? {
        val validName = validator.textValidation(name)
        val validDate = validator.dateValidation(date)
        val validWork = validator.textValidation(work)
        val validAmount = validator.numberValidation(amounts)
        return if (!validName!! || !validDate!! || !validWork!! || !validAmount!!) {
            Toast.makeText(context, "Your data is incorrect!\nतपाईको डाटा गलत छ!\n", Toast.LENGTH_SHORT).show()
            false
        } else {
            return setUpdateCustomer(id, name, date, work, amounts, context)
        }
    }

    private fun setUpdateCustomer(id: Long?, name: String?, date: String?, work: String?, amounts: String?, context: Context?): Boolean? {
        val isSuccess = homeModel.getUpdate(id, name, date, work, amounts, context)
        return if (isSuccess == false){
            Toast.makeText(context, "On expected data is not update.", Toast.LENGTH_SHORT).show()
            false
        } else {
            Toast.makeText(context, "डाटा Update गरिएको छ।", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}