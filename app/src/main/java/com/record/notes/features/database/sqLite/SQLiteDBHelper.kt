package com.record.notes.features.database.sqLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.record.notes.features.home.CustomerPojo
import java.sql.SQLException

class SQLiteDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VIRION) {

    // create the companion object
    companion object {
        // initialize the variable
        private const val DATABASE_NAME = "CustomerDB"
        private const val DATABASE_VIRION = 1

        // user table
        const val CUSTOMER_TABLE = "customer"
        const val CUSTOMER_ID = "id"
        const val CUSTOMER_NAME = "customer_name"
        const val RECORDING_DATE = "date"
        const val WORK = "work"
        const val DUE_AMOUNTS = "due_amounts"
    }

    // create the database
//    override fun onCreate(db: SQLiteDatabase) {
//        // create the database table
//        db.execSQL(
//            " CREATE TABLE " + CUSTOMER_TABLE +
//                    "(" + CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUSTOMER_NAME + " TEXT UNIQUE, " + RECORDING_DATE + " TEXT, " + WORK + " TEXT, " + DUE_AMOUNTS + " TEXT " + ")"
//        )
//    }
    override fun onCreate(db: SQLiteDatabase) {
        // create the database table
        db.execSQL("CREATE TABLE $CUSTOMER_TABLE(" +
                    "$CUSTOMER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$CUSTOMER_NAME TEXT UNIQUE," +
                    "$RECORDING_DATE TEXT," +
                    "$WORK TEXT," +
                    "$DUE_AMOUNTS TEXT" +
                    ")"
        )
    }

    // CRUD statements
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // drop the database table
        db.execSQL("DROP TABLE IF EXISTS $CUSTOMER_TABLE")
        onCreate(db)
    }

    // insert the user
    fun recordCustomerDetails(name: String, date: String, work: String, amounts: String): Boolean? {
        return try {
            val sqLiteDatabaseWrite = this.writableDatabase // write only Insert, update, delete query
            val values = ContentValues()
            values.put(CUSTOMER_NAME, name)
            values.put(RECORDING_DATE, date)
            values.put(WORK, work)
            values.put(DUE_AMOUNTS, amounts)

            Log.e("Customer Name:", name)
            Log.e("Customer Date:", date)
            Log.e("Customer Work:", work)
            Log.e("Customer Amount:", amounts)

            sqLiteDatabaseWrite.insert(CUSTOMER_TABLE, null, values) // insert the user data in database
            sqLiteDatabaseWrite.close()
            true
        } catch (ex: SQLException) {
            false
        }
    }

    // get data from database
    @SuppressLint("Range")
    fun getCustomerData(context: Context): ArrayList<CustomerPojo> {
        val customerList = ArrayList<CustomerPojo>()
        val sqLiteDatabaseRead = this.readableDatabase
        val cursor = sqLiteDatabaseRead.rawQuery("SELECT * FROM $CUSTOMER_TABLE", null)
        if (cursor.moveToFirst()) {
            do {
                // store the data in variables
                val customerId = cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)) // customer Id
                val customerName = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)) // customer name
                val recordDate = cursor.getString(cursor.getColumnIndex(RECORDING_DATE)) // date
                val customerWork = cursor.getString(cursor.getColumnIndex(WORK)) // work
                val customerAmounts =
                    cursor.getString(cursor.getColumnIndex(DUE_AMOUNTS)) // amounts
                // add all details in list
                customerList.add(
                    CustomerPojo(
                        customerId,
                        customerName,
                        recordDate,
                        customerWork,
                        customerAmounts
                    )
                )
            } while (cursor.moveToNext())
        } else {
            Toast.makeText(context, "The database has no data!\nडाटाबेससँग कुनै डाटा छैन!", Toast.LENGTH_SHORT).show()
        }
        sqLiteDatabaseRead.close()
        cursor.close()
        return customerList
    }

    // get email
    @SuppressLint("Recycle")
    fun deleteCustomerById(customerId: String, customerName: String, context: Context): Boolean? {
        val sqLiteDatabaseRead = this.readableDatabase
        val sqLiteDatabaseWrite = this.writableDatabase
        try {
            val cursor = sqLiteDatabaseRead.rawQuery("SELECT * FROM $CUSTOMER_TABLE", null)
            while (cursor.moveToNext()) {
                if (customerId == cursor.getString(0) && customerName == cursor.getString(1)) {
                    Log.e("Customer Id:", customerId)
                    val deleteQuery = "DELETE FROM $CUSTOMER_TABLE WHERE $CUSTOMER_ID = ? AND $CUSTOMER_NAME = ?"
                    sqLiteDatabaseWrite.execSQL(deleteQuery, arrayOf(customerId, customerName))
                    cursor.close()
                    return true
                }
            }
            cursor.close()
            sqLiteDatabaseRead.close()
            sqLiteDatabaseWrite.close()
            return false

        } catch (ex: SQLException) {
            Toast.makeText(context, "SQL exception", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // update password
    @SuppressLint("Recycle")
    fun updateCustomer(id: String, name: String, date: String, work: String, amounts: String, context: Context): Boolean {
        val sqLiteDatabaseRead = this.readableDatabase
        val sqLiteDatabaseWrite = this.writableDatabase
        try {
            val cursor = sqLiteDatabaseRead.rawQuery("SELECT $CUSTOMER_ID, $CUSTOMER_NAME FROM $CUSTOMER_TABLE", null)
            while (cursor.moveToNext()) {
                val customerId = cursor.getString(0)
//                val customerName = cursor.getString(1)
                if (id == customerId) {
                    val contentValues = ContentValues()
                    contentValues.put(CUSTOMER_ID, id)
                    contentValues.put(CUSTOMER_NAME, name)
                    contentValues.put(RECORDING_DATE, date)
                    contentValues.put(WORK, work)
                    contentValues.put(DUE_AMOUNTS, amounts)
                    sqLiteDatabaseWrite.update(CUSTOMER_TABLE, contentValues, "$CUSTOMER_ID=$id", null)
                    cursor.close()
                    return true
                }
            }
            cursor.close()
            return false
        } catch (ex: SQLException) {
            return false
        }
    }
//    @SuppressLint("Recycle")
//    fun updateCustomer(name: String, date: String, work: String, amounts: String, context: Context): Boolean? {
//        val sqLiteDatabaseRead = this.readableDatabase
//        val sqLiteDatabaseWrite = this.writableDatabase
//        try {
//            val cursor = sqLiteDatabaseRead.rawQuery("SELECT $CUSTOMER_ID, $CUSTOMER_NAME FROM $CUSTOMER_TABLE", null)
//            while (cursor.moveToNext()) {
//                val id = cursor.getShort(0)
//                val customerName = cursor.getString(1)
//                return if (name == customerName){
//                    val contentValues = ContentValues()
//                    contentValues.put(CUSTOMER_ID, id)
//                    contentValues.put(CUSTOMER_NAME, name)
//                    contentValues.put(RECORDING_DATE, date)
//                    contentValues.put(WORK, work)
//                    contentValues.put(DUE_AMOUNTS, amounts)
//                    sqLiteDatabaseWrite.update(CUSTOMER_TABLE, contentValues, "id=$id", null)
//                    true
//                } else {
//                    return false
//                }
//            }
//            sqLiteDatabaseWrite.close()
//            return false
//        } catch (ex: SQLException ){
//            return false
//        }
//    }
}


//return try {
//    val sqLiteDatabaseWrite = this.writableDatabase
//    return if (id.isEmpty())  {
//        Toast.makeText(context, "Enter the new details!", Toast.LENGTH_SHORT).show()
//        false
//    } else {
//        val updateQuery = "UPDATE ${SQLiteDBHelper.CUSTOMER_TABLE} SET ${SQLiteDBHelper.CUSTOMER_NAME} = ? AND ${SQLiteDBHelper.RECORDING_DATE} = ? AND ${SQLiteDBHelper.WORK} = ? AND ${SQLiteDBHelper.DUE_AMOUNTS} = ? WHERE ${SQLiteDBHelper.CUSTOMER_ID} = ?"
//        val args = arrayOf(name, date, work, amounts, id)
//        sqLiteDatabaseWrite.execSQL(updateQuery, null)
//        return true
//    }
//} catch (ex: Exception) {
//    Toast.makeText(context, "Enter the valid strong password!", Toast.LENGTH_SHORT).show()
//    false
//}