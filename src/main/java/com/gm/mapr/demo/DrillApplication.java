package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.drill.sql.parser.DrillParser;
import com.gm.drill.sql.parser.impl.DrillSqlBuilderVisitor;
import com.gm.drill.sql.parser.impl.QueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gm.mapr.demo.DrillRepository.TABLE_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
@Configuration
public class DrillApplication {

    public static String DRILL_JDBC_URL = "jdbc:drill:drillbit=localhost;maprdemo";
    public static String JDBC_DRIVER = "org.apache.drill.jdbc.Driver";


    public static void main(String[] args) {
        SpringApplication.run(DrillApplication.class, args);
    }


    @Bean
    DrillDefaultMethodInvokingMethodInterceptor drillDefaultMethodInvokingMethodInterceptor() {

        return new DrillDefaultMethodInvokingMethodInterceptor();
    }

    @Bean
    public DrillRepository drillRepository(ApplicationContext applicationContext) throws ClassNotFoundException {
        DrillRepositoryFactoryBean fb = new DrillRepositoryFactoryBean();
        return fb.createDrillRepository(DrillRepository.class, applicationContext.getAutowireCapableBeanFactory());
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

        @Autowired
        private DrillRepository drillRepository;
        @Autowired
        private JdbcTemplate jdbcTemplate;

        /**
         * Can specify connection URL in 2 ways.
         * 1. Connect to Zookeeper - "jdbc:drill:zk=<hostname/host-ip>:5181/drill/<cluster-name>-drillbits"
         * 2. Connect to Drillbit - "jdbc:drill:drillbit=<hostname>"
         */


        @GetMapping("/users/{name}/{since}")
        public List<JsonNode> users(@PathVariable String name, @PathVariable String since) throws SQLException, ClassNotFoundException {


            return drillRepository.getYelpBySinceAndName(name, since);

        }

        @RequestMapping(method = GET, value = "/users", produces = {APPLICATION_JSON_VALUE})
        public List<JsonNode> query(@RequestParam(value = "fn") String fn,@RequestParam(value = "search") String search) throws SQLException, IOException {

            List<JsonNode> yelpObjects = new ArrayList<>();

            DrillParser drillParser = new DrillParser();
            QueryContext qc = new QueryContext(TABLE_NAME,fn);
            drillParser.parse(search).accept(new DrillSqlBuilderVisitor(), qc);

            String sql = qc.getQuery().toString();
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            ResultSet result = ((ResultSetWrappingSqlRowSet) results).getResultSet();

            ObjectMapper objectMapper = new ObjectMapper();

            //  Generic payload creation
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

            return yelpObjects;

        }


    }


}
