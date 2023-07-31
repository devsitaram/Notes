package com.record.notes.features.database.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    // initialize the variable
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public int id;
    @ColumnInfo(name = "user_email")
    public String userEmail;
    @ColumnInfo(name = "user_name")
    public String nameName;
    @ColumnInfo(name = "user_password")
    public String userPassword;

    // contracture
    public User(String userEmail, String nameName, String userPassword) {
        this.userEmail = userEmail;
        this.nameName = nameName;
        this.userPassword = userPassword;
    }
}
