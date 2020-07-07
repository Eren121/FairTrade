package fr.rafoudiablol.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Executor {
    String subCommand() default "";
}
