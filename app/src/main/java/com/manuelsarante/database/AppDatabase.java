package com.manuelsarante.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.domain.Notas;

@Database(entities = {Notas.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NotasDao notasDao();
    public static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE==null){

            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "notas.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return INSTANCE;

    }

}
