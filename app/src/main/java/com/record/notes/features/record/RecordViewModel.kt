package com.record.notes.features.record

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.record.notes.features.util.InputValidator

class RecordViewModel: ViewModel() {

    private val validator = InputValidator()
    private val recordModel = RecordModel()

    // get the data from view and notify the record is complete or not
    fun getRecordDetails(name: String, date: String, work: String, amounts: String, context: Context): Boolean {
        val validName = validator.textValidation(name)
        val validDate = validator.dateValidation(date)
        val validWork = validator.textValidation(work)
        val validAmount = validator.numberValidation(amounts)
        return if (!validName || !validDate || !validWork || !validAmount){
            Toast.makeText(context, "Your data is incorrect!\nतपाईको डाटा गलत छ!\n", Toast.LENGTH_SHORT).show()
            false
        } else {
            return setCustomerDetail(name, date, work, amounts, context)
        }
    }

    // get data for check the validation
    private fun setCustomerDetail(name: String, date: String, work: String, amounts: String, context: Context): Boolean {
        val isSuccess = recordModel.getRecordingData(name, date, work, amounts, context)
        return if (isSuccess==false){
            Toast.makeText(context, "On expected data is not recorded.", Toast.LENGTH_SHORT).show()
            false
        } else {
            Toast.makeText(context, "डाटा रेकर्ड गरिएको छ।", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}




//    fun insertCustomerData(name: String, date: String, work: String, amounts: String, context: Context): Completable? {
//        val validName = validator.textValidation(name)
//        val validDate = validator.dateValidation(date)
//        val validWork = validator.textValidation(work)
//        val validAmount = validator.numberValidation(amounts)
//        return if (!validName || !validDate || !validWork || !validAmount){
//            Toast.makeText(context, "Your data is incorrect!\nतपाईको डाटा गलत छ!\n", Toast.LENGTH_SHORT).show()
//            null // Return null when data is incorrect
//        } else {
//            val customerList = ArrayList<Customer>()
//            customerList.add(Customer(name, date, work, amounts))
//            return getCustomerDetail(customerList, context)
//        }
//    }
//
//    private fun getCustomerDetail(customerList: ArrayList<Customer>, context: Context): Completable? {
//        val customer = recordModel.getRecordCustomer(customerList, context)
//        return if (customer == null) {
//            Toast.makeText(context, "On expected data is not recorded.", Toast.LENGTH_SHORT).show()
//            Completable.never() // Return an empty Completable when data is not recorded
//        } else {
//            Toast.makeText(context, "डाटा रेकर्ड गरिएको छ।", Toast.LENGTH_SHORT).show()
//            return customer
//        }
//    }