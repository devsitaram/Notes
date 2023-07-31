package com.record.notes.features.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface CustomerDao {

    // insert customer data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customerList: List<Customer>): Completable?

    // Get all users from the database
    @Query("SELECT * FROM customer")
    fun getAllCustomer(): LiveData<List<Customer?>?>?

    // this method can get the username and password
    @Query("SELECT EXISTS(SELECT * FROM customer WHERE customer_name =:name AND record_date =:date AND customer_work =:work AND customer_amounts =:amounts)")
    fun loginDetails(name: String?, date: String?, work: String?, amounts: String?): Boolean

    // Delete the user with a specific username
    @Query("DELETE FROM customer WHERE customer_id = :customerId")
    fun deleteUserById(customerId: Long): Completable?
}