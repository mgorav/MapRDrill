package com.gm.mapr.demo;

import org.junit.Test;
import org.springframework.core.SpringVersion;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.util.Version;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

public class QueryMethodParsingUnitTests {

    private static final Version SPRING_VERSION = Version.parse(SpringVersion.getVersion());

    RepositoryMetadata metadata = new DefaultRepositoryMetadata(DrillRepositoryTesting.class);

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Test
    public void testCollectionQueryMetaData() throws Exception {
        Method method = DrillRepositoryTesting.class.getMethod("getYelpBySinceAndName",String.class,String.class);
        QueryMethod queryMethod = new QueryMethod(method, metadata, factory);
        assertThat(queryMethod.isCollectionQuery()).isTrue();

    }


    @Test
    public void testEntityBeingReturned() throws Exception {
        Method method = DrillRepositoryTesting.class.getMethod("getById", String.class);
        QueryMethod queryMethod = new QueryMethod(method, metadata, factory);
        assertThat(queryMethod.isQueryForEntity()).isTrue();
    }


    @Test
    public void testQueryString() throws Exception {

        DrillQueryMethod method = getQueryMethod(DrillRepositoryTesting.class, "getYelpBySinceAndName",String.class,String.class);

        assertEquals("YelpObject.getYelpBySinceAndName", method.getNamedQueryName());
        assertThat(method.isCollectionQuery()).isTrue();
        System.out.println(method.getAnnotatedQuery());
        assertThat(!method.getAnnotatedQuery().isEmpty());
        assertThat(method.getParameters().getNumberOfParameters() == 2);
    }



    private DrillQueryMethod getQueryMethod(Class<?> repositoryInterface, String methodName, Class<?>... parameterTypes)
            throws Exception {

        Method method = repositoryInterface.getMethod(methodName, parameterTypes);
        return new DrillQueryMethod(method, metadata, factory);
    }


}
