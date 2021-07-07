package com.roooqaTv.roooqatv.data.locale.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void addItem(RoomVideos... item);

    @Query("DELETE FROM RoomVideos WHERE itemId = :ids")
    int deleteById(int ids);

//    @Delete
//    void removeItem(RoomVideos... item);

    @Query("select * from RoomVideos")
    List<RoomVideos> getAll();

    @Query("delete from RoomVideos")
    void delAll();

    @Update
    void update(RoomVideos... item);

//    @Query("SELECT * FROM RoomVideos")
//    List<RoomVideos> getAllItems();

}
