package com.asep.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TestInformation {
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    Priority priority() default Priority.HIGH;

    String[] members() default "";

    String description() default "";

    String createdBy() default "Arindam Dutta";

    String dateOfSubmission() default "04/12/2019";
}