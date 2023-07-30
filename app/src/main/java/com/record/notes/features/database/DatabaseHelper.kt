package com.record.notes.features.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.record.notes.features.home.CustomerPojo
import java.sql.SQLException

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VIRION) {

    // create the companion object
    companion object {
        // initialize the variable
        private const val DATABASE_NAME = "CustomerDB"
        private const val DATABASE_VIRION = 1

        // user table
        const val CUSTOMER_TABLE = "customer"
        const val CUSTOMER_ID = "id"
        const val RECORDING_DATE = "date"
        const val CUSTOMER_NAME = "customer_name"
        const val WORK = "work"
        const val DUE_AMOUNTS = "due_amounts"

        private var userId: String? = null
        private var oldPassword: String? = null
        // data table
    }

    // create the database
    override fun onCreate(db: SQLiteDatabase) {
        // create the database table
        db.execSQL(
            " CREATE TABLE " + CUSTOMER_TABLE +
                    "(" + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RECORDING_DATE + " TEXT, " + CUSTOMER_NAME + " TEXT, "+ WORK + " TEXT, " + DUE_AMOUNTS + " TEXT " + ")"
        )
    }

    // CRUD statements
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // drop the database table
        db.execSQL("DROP TABLE IF EXISTS $CUSTOMER_TABLE")
        onCreate(db)
    }

    // insert the user
    fun recordCustomerDetails(date: String, name: String, work: String, amounts: String): Boolean? {
        return try {
            val databaseWrite = this.writableDatabase // write only Insert, update, delete query
            val values = ContentValues()
            values.put(RECORDING_DATE, date)
            values.put(CUSTOMER_NAME, name)
            values.put(WORK, work)
            values.put(DUE_AMOUNTS, amounts)

//            Log.e("Customer Date:", date)
//            Log.e("Customer Name:", name)
//            Log.e("Customer Work:", work)
//            Log.e("Customer Amount:", amounts)

            databaseWrite.insert(CUSTOMER_TABLE, null, values) // insert the user data in database
            true
        } catch (ex: SQLException) {
            false
        }
    }

    // get data from database
    @SuppressLint("Recycle", "Range")
    fun getCustomerData(context: Context): ArrayList<CustomerPojo> {
        val customerList = ArrayList<CustomerPojo>()
        val sqLiteDatabaseRead = this.readableDatabase
        val cursor = sqLiteDatabaseRead.rawQuery("SELECT * FROM $CUSTOMER_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                // store the data in variables
                val recordDate = cursor.getString(cursor.getColumnIndex(RECORDING_DATE)) // date
                val customerName = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)) // customer name
                val customerWork = cursor.getString(cursor.getColumnIndex(WORK)) // work
                val customerAmounts = cursor.getString(cursor.getColumnIndex(DUE_AMOUNTS)) // amounts
                // add all details in list
                customerList.add(CustomerPojo(recordDate, customerName, customerWork, customerAmounts))
            } while (cursor.moveToNext())
        } else {
            Toast.makeText(context, "The database has no data!\nडाटाबेससँग कुनै डाटा छैन!", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        return customerList
    }

    // get email
//    fun getUserEmail(email: String): Boolean {
//        // create the object of sqLiteDatabase and call the getReadableDatabase methods
//        val sqLiteDatabaseRead = this.readableDatabase
//        val cursor = sqLiteDatabaseRead.rawQuery("SELECT * FROM $USER_TABLE", null)
//
//        // use the while loop
//        while (cursor.moveToNext()) {
//            // check the user login details are valid or not
//            if (email == cursor.getString(1)) {
//                userId = cursor.getString(0)
//                oldPassword = cursor.getString(3)
//                cursor.close()
//                return true
//            }
//        }
//        cursor.close()
//        return false
//    }

    // update password
//    @SuppressLint("Recycle")
//    fun updatePassword(newPassword: String, context: Context): Boolean? {
//        return try {
//            val sqLiteDatabaseWrite = this.writableDatabase
//            return if (oldPassword == newPassword)  {
//                Toast.makeText(context, "Enter the new password!", Toast.LENGTH_SHORT).show()
//                false
//            } else {
//                val updateQuery = "UPDATE $USER_TABLE SET $USER_PASSWORD = ? WHERE $USER_ID = ?"
//                val args = arrayOf(newPassword, userId)
//                sqLiteDatabaseWrite.execSQL(updateQuery, args)
//                return true
//            }
//        } catch (ex: Exception) {
//            Toast.makeText(context, "Enter the valid strong password!", Toast.LENGTH_SHORT).show()
//            false
//        }
//    }
}