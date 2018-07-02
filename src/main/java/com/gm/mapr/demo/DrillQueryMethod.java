package com.gm.mapr.demo;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class DrillQueryMethod extends QueryMethod {
    private final Method method;
    /**
     * Creates a new {@link QueryMethod} from the given parameters. Looks up the correct query to use for following
     * invocations of the method given.
     *
     * @param method   must not be {@literal null}.
     * @param metadata must not be {@literal null}.
     * @param factory  must not be {@literal null}.
     */
    public DrillQueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory) {
        super(method, metadata, factory);
        this.method = method;
    }

    /**
     * Returns the query string declared in a {@link Query} annotation or {@literal null} if neither the annotation found
     * nor the attribute was specified.
     *
     * @return
     */
    String getAnnotatedQuery() {

        String query = getAnnotationValue("value", String.class);
        return StringUtils.hasText(query) ? query : null;
    }


    /**
     * Returns the {@link Query} annotation's attribute casted to the given type or default value if no annotation
     * available.
     *
     * @param attribute
     * @param type
     * @return
     */
    private <T> T getAnnotationValue(String attribute, Class<T> type) {
        return getMergedOrDefaultAnnotationValue(attribute, Query.class, type);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> T getMergedOrDefaultAnnotationValue(String attribute, Class annotationType, Class<T> targetType) {

        Annotation annotation = AnnotatedElementUtils.findMergedAnnotation(method, annotationType);
        if (annotation == null) {
            return targetType.cast(AnnotationUtils.getDefaultValue(annotationType, attribute));
        }

        return targetType.cast(AnnotationUtils.getValue(annotation, attribute));
    }
}
