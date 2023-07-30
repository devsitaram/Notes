package com.record.notes.features.util

import java.util.Date

class InputValidator {

    // check the username validation
    fun textValidation(username: String): Boolean {
        val nameRegex = Regex("[A-Za-z\\s]+")
        return username.matches(nameRegex)
    }

    // check the input date validation
    fun dateValidation(date: String): Boolean {
        val dateRegex = Regex("""\d{2}-\d{2}-\d{4}""")
        return date.matches(dateRegex)
    }

    // check the number where int and double both support
    fun numberValidation(number: String): Boolean {
        val intRegex = Regex("-?\\d+")
        val doubleRegex = Regex("-?\\d+\\.\\d+")
        return number.matches(intRegex) || number.matches(doubleRegex)
    }
}