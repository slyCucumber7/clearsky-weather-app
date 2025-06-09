package com.github.slycucumber7.clearskies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context,"clearskyapp.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateAllEntries(DataModel model){

    }

    public String[] getMetadata(){
        String time,lattitude,longitude,measurement_unit;
        String[] metadata = new String[4];
        return metadata;
    }


}
