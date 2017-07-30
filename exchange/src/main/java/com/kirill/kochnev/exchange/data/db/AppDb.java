package com.kirill.kochnev.exchange.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kirill.kochnev.exchange.data.db.dao.TickDao;
import com.kirill.kochnev.exchange.data.db.models.TickDb;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */
@Database(entities = {TickDb.class}, version = 2)
public abstract class AppDb extends RoomDatabase {

    public abstract TickDao getTickDao();
}
