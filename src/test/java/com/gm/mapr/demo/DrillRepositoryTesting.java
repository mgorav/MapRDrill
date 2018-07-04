package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface DrillRepositoryTesting extends Repository<JsonNode, Serializable> {
    public static final String TABLE_NAME = "/mapr/maprdemo.mapr.io/apps/user";

    @Query("SELECT name, yelping_since, support" + " FROM dfs.`" + TABLE_NAME + "` "
            + " WHERE yelping_since = '?since" + "'" +
            " AND name = '?name" + "'")
    List<JsonNode> getYelpBySinceAndName(@Param("name") String name, @Param("since") String since);

    JsonNode getById(String id);


}
