package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gm.drill.sql.parser.DrillQueryStringParser;
import com.gm.drill.sql.parser.impl.DrillSqlBuilderQueryStringVisitor;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.gm.util.JsonUtil.getJsonNode;
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

        @RequestMapping(method = GET, value = "/{resourceName}", produces = {APPLICATION_JSON_VALUE})
        public List<JsonNode> query(@PathVariable String resourceName,@RequestParam(value = "fn") String fn,@RequestParam(value = "search") String search) throws SQLException, IOException {

            List<JsonNode> yelpObjects = new ArrayList<>();
            // Fully qualified global name space following by the resourceName.
            // NOTE: resourceName = tableName
            String tableName = "dfs.`" + "/mapr/maprdemo.mapr.io/apps/" + resourceName + "` ";
            DrillQueryStringParser drillQueryStringParser = new DrillQueryStringParser();
            QueryContext qc = new QueryContext(tableName,fn);
            drillQueryStringParser.parse(search).accept(new DrillSqlBuilderQueryStringVisitor(), qc);

            String sql = qc.getQuery().toString();
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            ResultSet result = ((ResultSetWrappingSqlRowSet) results).getResultSet();

            ObjectMapper objectMapper = new ObjectMapper();

            //  Generic payload creation
            getJsonNode(yelpObjects, result, objectMapper);

            return yelpObjects;

        }


    }


}
