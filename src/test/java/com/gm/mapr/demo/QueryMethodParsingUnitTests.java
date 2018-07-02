package com.gm.mapr.demo;

import org.junit.Test;
import org.springframework.core.SpringVersion;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.util.Version;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryMethodParsingUnitTests {

    private static final Version SPRING_VERSION = Version.parse(SpringVersion.getVersion());

    RepositoryMetadata metadata = new DefaultRepositoryMetadata(DrillRepositoryTesting.class);

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Test
    public void testCollectionQueryMetaData() throws Exception {
        Method method = DrillRepositoryTesting.class.getMethod("getYelpBySinceAndName");
        QueryMethod queryMethod = new QueryMethod(method, metadata, factory);
        assertThat(queryMethod.isCollectionQuery()).isTrue();
    }


    @Test
    public void testEntityBeingReturned() throws Exception {
        Method method = DrillRepositoryTesting.class.getMethod("getById", String.class);
        QueryMethod queryMethod = new QueryMethod(method, metadata, factory);
        assertThat(queryMethod.isQueryForEntity()).isTrue();
    }


    public static interface SomeRepository extends Repository<Payment, Serializable> {

        Iterable<String> sampleMethod();
    }

    public static class Payment {

    }

}
