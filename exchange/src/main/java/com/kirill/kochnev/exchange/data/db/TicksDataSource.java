package com.kirill.kochnev.exchange.data.db;

import com.kirill.kochnev.exchange.data.db.dao.TickDao;
import com.kirill.kochnev.exchange.data.db.models.TickDb;
import com.kirill.kochnev.exchange.data.enums.ToolType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class TicksDataSource {

    private TickDao dao;

    public TicksDataSource(TickDao dao) {
        this.dao = dao;
    }

    public List<TickDb> getTicksByToolType(ToolType type) {
        return dao.getTicksByToolType(type.toString());
    }

    public List<TickDb> getSubscribedTicks(List<ToolType> types) {
        List<String> dbToolType = new ArrayList<>();
        for (ToolType type : types) {
            dbToolType.add(type.toString());
        }
        return dao.getSubscribedTicks(dbToolType, dbToolType.size());
    }

    public void putTicks(List<TickDb> tickDbs) {
        dao.putTicks(tickDbs);
    }

    public void deleteAllTicksByToolType(ToolType toolType) {
        dao.deleteTicks(toolType.toString());
    }
}
