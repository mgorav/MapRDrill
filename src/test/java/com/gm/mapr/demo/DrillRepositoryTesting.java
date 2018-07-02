package com.gm.mapr.demo;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface DrillRepositoryTesting extends Repository<YelpObject, Serializable> {
    public static final String TABLE_NAME = "/mapr/maprdemo.mapr.io/apps/user";

    @Query("SELECT name, yelping_since, support" + " FROM dfs.`" + TABLE_NAME + "` "
            + " WHERE yelping_since = '?since" + "'" +
            " AND name = '?name" + "'")
    List<YelpObject> getYelpBySinceAndName(@Param("name") String name, @Param("since") String since);

    YelpObject getById(String id);


}
