package com.octanna.lib.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command {
    String[] aliases() default {""};

    String usage() default "";

    String about() default "No command description.";

    String permission() default "";

    String title() default "";

    int requiredArgs() default 0;
}

