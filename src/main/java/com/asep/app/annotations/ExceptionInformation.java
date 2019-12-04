package com.asep.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExceptionInformation {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    String description() default "";

    Priority priority() default Priority.LOW;

}
