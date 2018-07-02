package com.gm.mapr.demo;

import org.springframework.data.annotation.QueryAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@QueryAnnotation
@Documented
public @interface Query {
    /**
     * Defines the Drill query to be executed when the annotated method is called.
     */
    String value() default "";
}
