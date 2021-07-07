package com.roooqaTv.roooqatv.data.locale.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RoomVideos.class}, version =1, exportSchema = false)


public abstract class RoomManger extends RoomDatabase {
    public abstract RoomDao roomDao();

    public static RoomManger roomManger;

    public static synchronized RoomManger getInistance(Context context) {
        if (roomManger == null) {
            roomManger = Room.databaseBuilder(context.getApplicationContext(), RoomManger.class,
                    "videos")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomManger;
    }
}
