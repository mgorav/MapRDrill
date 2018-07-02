package com.gm.mapr.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

public interface DrillRepositoryTesting extends Repository<YelpObject,Serializable> {
    public static final String TABLE_NAME = "/mapr/maprdemo.mapr.io/apps/user";

    @Query("SELECT name, yelping_since, support" +  " FROM dfs.`" + TABLE_NAME + "` "
            + " WHERE yelping_since = '?since"  + "'" +
            " AND name = '?name"  + "'")
    List<YelpObject> getYelpBySinceAndName();

    YelpObject getById(String id);


}
