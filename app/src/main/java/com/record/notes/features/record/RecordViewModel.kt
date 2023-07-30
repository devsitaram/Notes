package com.record.notes.features.record

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.record.notes.features.util.InputValidator

class RecordViewModel: ViewModel() {

    private val validator = InputValidator()
    // get the data from view and notify the record is complete or not
    fun recordDetails(name: String, date: String, work: String, amounts: String, context: Context): Boolean {
        val validName = validator.textValidation(name)
        val validDate = validator.dateValidation(date)
        val validWork = validator.textValidation(work)
        val validAmount = validator.numberValidation(amounts)

         return if (validName && validDate && validWork && validAmount){
             Toast.makeText(context, "It is valid", Toast.LENGTH_SHORT).show()
             true
        } else {
            Toast.makeText(context, "It is not valid!", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}