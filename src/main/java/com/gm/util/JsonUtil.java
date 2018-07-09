package com.gm.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public abstract class JsonUtil {

    public static void getJsonNode(List<JsonNode> yelpObjects, ResultSet result, ObjectMapper objectMapper) throws SQLException, IOException {
        while (result.next()) {

            ResultSetMetaData meta = result.getMetaData();

            StringBuilder node = new StringBuilder();

            node.append("{ ");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String value = result.getString(i);

                value = (value == null ? "\"" + ":" + null : "\"" + " : " + "\"" + value + "\"");

                node.append(" \"" + meta.getColumnName(i) + value);

                if (i < meta.getColumnCount()) {
                    node.append(",");
                }
            }
            node.append("}");
            yelpObjects.add(objectMapper.readTree(node.toString()));

        }
    }
}
