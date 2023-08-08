package com.record.notes.features.database.sqLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.record.notes.features.home.CustomerPojo
import java.sql.SQLException

class SQLiteDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VIRION) {

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
    override fun onCreate(db: SQLiteDatabase) {
        // create the database table
        db.execSQL(
            "CREATE TABLE $CUSTOMER_TABLE(" +
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
            val sqLiteWrite = this.writableDatabase // write only Insert, update, delete query
            val values = ContentValues()
            values.put(CUSTOMER_NAME, name)
            values.put(RECORDING_DATE, date)
            values.put(WORK, work)
            values.put(DUE_AMOUNTS, amounts)

            Log.e("Customer Name:", name)
            Log.e("Customer Date:", date)
            Log.e("Customer Work:", work)
            Log.e("Customer Amount:", amounts)

            sqLiteWrite.insert(CUSTOMER_TABLE, null, values) // insert the user data in database
            sqLiteWrite.close()
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
        while (cursor.moveToNext()) {
            // store the data in variables
            val customerId = cursor.getLong(cursor.getColumnIndex(CUSTOMER_ID))
            val customerName = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME))
            val recordDate = cursor.getString(cursor.getColumnIndex(RECORDING_DATE))
            val customerWork = cursor.getString(cursor.getColumnIndex(WORK))
            val customerAmounts = cursor.getString(cursor.getColumnIndex(DUE_AMOUNTS))

            // add all details to the list
            customerList.add(
                CustomerPojo(
                    customerId,
                    customerName,
                    recordDate,
                    customerWork,
                    customerAmounts
                )
            )
        }
        cursor.close()
        sqLiteDatabaseRead.close()
        if (customerList.isEmpty()) {
            Toast.makeText(
                context,
                "The database has no data!\nडाटाबेससँग कुनै डाटा छैन!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return customerList
    }

    // get email
    fun deleteCustomerById(customerId: Long?): Boolean {
        val dbWritable = this.writableDatabase
        val deletedRows =
            dbWritable.delete(CUSTOMER_TABLE, "$CUSTOMER_ID = ?", arrayOf(customerId.toString()))
        dbWritable.close()
        return deletedRows > 0
    }

    @SuppressLint("Recycle")
    fun deleteCustomer(name: String?): Boolean {
        val deleteDB: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = deleteDB.rawQuery("SELECT $CUSTOMER_NAME FROM $CUSTOMER_TABLE WHERE $CUSTOMER_NAME = ?", arrayOf(name))
        return if (cursor.count > 0) {
            Log.e("Name:-> ", "$name")
            deleteDB.execSQL("DELETE FROM $CUSTOMER_TABLE WHERE $CUSTOMER_NAME = ?", listOf(name).toTypedArray())
            Log.e("Result:-> ", "Deleted successfully")
            true
        } else {
            false
        }
    }

    // update password
    fun updateCustomer(
        id: Long?,
        name: String?,
        date: String?,
        work: String?,
        amounts: String?,
        context: Context?
    ): Boolean {
        val sqLiteDatabaseRead = this.readableDatabase
        val sqLiteDatabaseWrite = this.writableDatabase
        try {
            val cursor: Cursor =
                sqLiteDatabaseRead.rawQuery("SELECT $CUSTOMER_ID FROM $CUSTOMER_TABLE", null)
            while (cursor.moveToNext()) {
                val customerId = cursor.getLong(0)
                if (id == customerId) {
                    val contentValues = ContentValues()
                    contentValues.put(CUSTOMER_ID, id)
                    contentValues.put(CUSTOMER_NAME, name)
                    contentValues.put(RECORDING_DATE, date)
                    contentValues.put(WORK, work)
                    contentValues.put(DUE_AMOUNTS, amounts)

                    sqLiteDatabaseWrite.update(
                        CUSTOMER_TABLE,
                        contentValues,
                        "$CUSTOMER_ID = ?",
                        arrayOf(id.toString())
                    )
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
}