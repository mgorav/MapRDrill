package com.gm.mapr.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Configuration
public class DrillApplication {

    public static String DRILL_JDBC_URL = "jdbc:drill:drillbit=localhost;maprdemo";
    public static String JDBC_DRIVER = "org.apache.drill.jdbc.Driver";


    public static void main(String[] args) {
        SpringApplication.run(DrillApplication.class, args);
    }

    @Bean
    @Primary
    public DataSource drillDataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl(DRILL_JDBC_URL);
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setValidationQuery("SELECT 1 FROM sys.version");
        return dataSource;

    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate() {

        return new JdbcTemplate(drillDataSource());
    }


    @RestController
    public static class MapRDrillApi {


        public static final String TABLE_NAME = "/mapr/maprdemo.mapr.io/apps/user";

        @Autowired
        private JdbcTemplate jdbcTemplate;

        /**
         * Can specify connection URL in 2 ways.
         * 1. Connect to Zookeeper - "jdbc:drill:zk=<hostname/host-ip>:5181/drill/<cluster-name>-drillbits"
         * 2. Connect to Drillbit - "jdbc:drill:drillbit=<hostname>"
         */


        @GetMapping("/users/{name}/{since}")
        public List<YelpObject> users(@PathVariable String name, @PathVariable String since) throws SQLException, ClassNotFoundException {


            //Username and password have to be provided to obtain connection.
            //Ensure that the user provided is present in the cluster / sandbox
            //Connection connection = DriverManager.getConnection(DRILL_JDBC_URL, "root", "");

            //Statement statement = connection.createStatement();

            final String sql = "SELECT name, yelping_since, support " +
                    " FROM dfs.`" + TABLE_NAME + "` " +
                    " WHERE yelping_since = '" + since + "'" +
                    " AND name = '" + name + "'";

            System.out.println("Query: " + sql);

            List<YelpObject> yelpObjects = new ArrayList<>();

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            ResultSet result = ((ResultSetWrappingSqlRowSet) results).getResultSet();

            while (result.next()) {

                yelpObjects.add(new YelpObject(result.getString(1), result.getString(2), result.getString(3)));
            }

            return yelpObjects;

        }


    }


}
