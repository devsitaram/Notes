package com.record.notes.features.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@SuppressWarnings("AndroidUnresolvedRoomSqlReference")
@Dao
public interface UserDao {

    // insert the user data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(List<User> userList);

    // this method can get the username and password
    @Query("SELECT EXISTS(SELECT * FROM user WHERE user_name =:nameName AND user_password =:userPassword)")
    boolean loginDetails(String nameName, String userPassword);

    // Delete the user with a specific username
    @Query("DELETE FROM user WHERE id = :userId")
    Completable deleteUserById(long userId);
}