package com.gm.mapr.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrillDefaultMethodInvokingMethodInterceptor extends DefaultMethodInvokingMethodInterceptor {
    RepositoryMetadata metadata = new DefaultRepositoryMetadata(DrillRepository.class);

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        List<JsonNode> yelpObjects = new ArrayList<>();

        DrillQueryMethod method = getQueryMethod(DrillRepository.class, invocation.getMethod().getName(), invocation.getMethod().getParameterTypes());

        // If @Query not specified, it's business as usual. Just call generic proxied repository (SimpleDrillRepository)
        if (method.getAnnotatedQuery().isEmpty()) {
            return super.invoke(invocation);
        }
        String sql = method.getAnnotatedQuery();

        int cnt = 0;
        for (Object argument : invocation.getArguments()) {
            String name = method.getParameters().getBindableParameter(cnt).getName().get();
            sql = sql.replaceAll("\\?" + name, argument.toString());
            cnt++;
        }

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        ResultSet result = ((ResultSetWrappingSqlRowSet) results).getResultSet();

        ObjectMapper objectMapper = new ObjectMapper();

        //  Generic payload creation
        getJsonNode(yelpObjects, result, objectMapper);

        return yelpObjects;

    }

    static void getJsonNode(List<JsonNode> yelpObjects, ResultSet result, ObjectMapper objectMapper) throws SQLException, IOException {
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


    private DrillQueryMethod getQueryMethod(Class<?> repositoryInterface, String methodName, Class<?>... parameterTypes)
            throws Exception {

        Method method = repositoryInterface.getMethod(methodName, parameterTypes);
        return new DrillQueryMethod(method, metadata, factory);
    }
}
