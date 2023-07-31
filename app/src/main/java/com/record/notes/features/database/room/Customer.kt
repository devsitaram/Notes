package com.record.notes.features.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
class Customer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "customer_id")
    var id = 0

    @field:ColumnInfo(name = "customer_name")
    var customerName: String? = null

    @field:ColumnInfo(name = "record_date")
    var recordDate: String? = null

    @field:ColumnInfo(name = "customer_work")
    var customerWork: String? = null

    @field:ColumnInfo(name = "customer_amounts")
    var customerAmounts: String? = null

    // contracture
    constructor(customerName: String?, recordDate: String?, customerWork: String?, customerAmounts: String?) {
        this.customerName = customerName
        this.recordDate = recordDate
        this.customerWork = customerWork
        this.customerAmounts = customerAmounts
    }
}