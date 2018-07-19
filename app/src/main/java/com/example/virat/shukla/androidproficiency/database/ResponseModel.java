package com.example.virat.shukla.androidproficiency.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "response_model")
public class ResponseModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    public int _ID;

    @NonNull
    @ColumnInfo(name="json_response")
    public String jsonResponse;
}
