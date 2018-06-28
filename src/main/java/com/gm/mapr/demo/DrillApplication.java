package com.gm.mapr.demo;

import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

@SpringBootApplication
public class DrillApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrillApplication.class, args);
    }

    @RestController
    public static class MapRDrillApi {

        public static String JDBC_DRIVER = "org.apache.drill.jdbc.Driver";
//        static {
//            try {
//                forName(JDBC_DRIVER);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        public static final String TABLE_NAME = "/mapr/maprdemo.mapr.io/apps/user";

        /**
         * Can specify connection URL in 2 ways.
         * 1. Connect to Zookeeper - "jdbc:drill:zk=<hostname/host-ip>:5181/drill/<cluster-name>-drillbits"
         * 2. Connect to Drillbit - "jdbc:drill:drillbit=<hostname>"
         */
        private static String DRILL_JDBC_URL = "jdbc:drill:drillbit=localhost;maprdemo";


        @GetMapping("/users/{name}/{since}")
        public List<YelpObject> users(@PathVariable String name, @PathVariable String since) throws SQLException, ClassNotFoundException {


            //Username and password have to be provided to obtain connection.
            //Ensure that the user provided is present in the cluster / sandbox
            Connection connection = DriverManager.getConnection(DRILL_JDBC_URL, "root", "");

            Statement statement = connection.createStatement();

            final String sql = "SELECT name, yelping_since, support " +
                    " FROM dfs.`" + TABLE_NAME + "` " +
                    " WHERE yelping_since = '" +  since + "'" +
                    " AND name = '" + name + "'" ;
            
            System.out.println("Query: " + sql);

            ResultSet result = statement.executeQuery(sql);

            List<YelpObject> yelpObjects = new ArrayList<>();
            while (result.next()) {

                yelpObjects.add(new YelpObject(result.getString(1),result.getString(2),result.getString(3)));
            }

            return yelpObjects;

        }


    }
}
