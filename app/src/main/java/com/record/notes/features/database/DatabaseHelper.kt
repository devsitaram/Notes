package com.record.notes.features.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VIRION) {

    // create the companion object
    companion object {
        // initialize the variable
        private const val DATABASE_NAME = "UserDB"
        private const val DATABASE_VIRION = 1
        // user table
        const val CUSTOMER_TABLE = "customer"
        const val USER_ID = "id"
        const val CUSTOMER_NAME = "customer_name"
        const val DATE = "date"
        const val WORK = "work"
        const val DUE_AMOUNTS = "due_amounts"

        private var userId: String? = null
        private var oldPassword: String? = null
        // data table
    }

    // create the database
    override fun onCreate(db: SQLiteDatabase) {
        // create the database table
        db.execSQL(" CREATE TABLE " + CUSTOMER_TABLE +
                    "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUSTOMER_NAME + " TEXT, " + DATE + " TEXT, " + WORK + " TEXT " + DUE_AMOUNTS + " TEXT " + ")"
        )
    }

    // CRUD statements
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // drop the database table
        db.execSQL("DROP TABLE IF EXISTS $CUSTOMER_TABLE")
        onCreate(db)
    }

    // insert the user
//    fun registerUser(email: String, username: String, userPassword: String): Boolean? {
//        return try {
//            val databaseWrite = this.writableDatabase // write only Insert, update, delete query
//            val values = ContentValues()
//            values.put(USER_EMAIL, email)
//            values.put(USER_NAME, username)
//            values.put(USER_PASSWORD, userPassword)
//            databaseWrite.insert(USER_TABLE, null, values) // insert the user data in database
//            true
//        } catch (ex: SQLException) {
//            false
//        }
//    }

    // get data from database
//    @SuppressLint("Recycle")
//    fun getLoginUsers(name: String, password: String): Boolean {
//        // create the object of sqLiteDatabase and call the getReadableDatabase methods
//        val sqLiteDatabaseRead = this.readableDatabase
//        val cursor = sqLiteDatabaseRead.rawQuery("SELECT * FROM $USER_TABLE", null)
//
//        // use the while loop
//        while (cursor.moveToNext()) {
//
//            // store the data in variable
//            val userName = cursor.getString(2) // username
//            val userPassword = cursor.getString(3) // password
//
//            // check the user login details are valid or not
//            if (name == userName && password == userPassword) {
//                cursor.close()
//                return true
//            }
//        }
//        cursor.close()
//        return false
//    }

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