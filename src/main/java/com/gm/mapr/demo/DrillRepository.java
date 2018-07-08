package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

@org.springframework.stereotype.Repository
public interface DrillRepository extends Repository<JsonNode, Serializable> {
    public static final String TABLE_NAME =  "dfs.`" + "/mapr/maprdemo.mapr.io/apps/user" + "` ";

    @Query("SELECT name, yelping_since, support" + " FROM " + TABLE_NAME
            + " WHERE yelping_since = '?datesince" + "'" +
            " AND name = '?name" + "'")
    List<JsonNode> getYelpBySinceAndName(@Param("name") String name, @Param("datesince") String datesince);


}
