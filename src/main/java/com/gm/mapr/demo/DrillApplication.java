package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
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

        /**
         * Can specify connection URL in 2 ways.
         * 1. Connect to Zookeeper - "jdbc:drill:zk=<hostname/host-ip>:5181/drill/<cluster-name>-drillbits"
         * 2. Connect to Drillbit - "jdbc:drill:drillbit=<hostname>"
         */


        @GetMapping("/users/{name}/{since}")
        public List<JsonNode> users(@PathVariable String name, @PathVariable String since) throws SQLException, ClassNotFoundException {


            return drillRepository.getYelpBySinceAndName(name, since);

        }


    }


}
