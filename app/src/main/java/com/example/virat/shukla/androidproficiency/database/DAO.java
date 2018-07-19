package com.example.virat.shukla.androidproficiency.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAO {
    @Query("SELECT * FROM response_model WHERE id == :jsonID")
    LiveData<ResponseModel> findJSONReponse(int jsonID);

    @Insert(onConflict = REPLACE)
    void insertNewResponse(ResponseModel response);

}
