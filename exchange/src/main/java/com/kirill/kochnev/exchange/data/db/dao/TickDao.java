package com.kirill.kochnev.exchange.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.enums.ToolType;

import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

@Dao
public interface TickDao {

    @Query("SELECT * FROM ticks WHERE tool_type = :toolType")
    List<TickDb> getTicksByToolType(String toolType);


    @Query("SELECT * FROM ticks WHERE tool_type IN (:type) ORDER BY creation_date LIMIT :count")
    List<TickDb> getSubscribedTicks(List<String> type, int count);

    @Insert
    void putTicks(List<TickDb> tickDbs);

}
