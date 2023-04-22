package dev.pvpb0t.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**

 Annotation to mark a method as an event hook. The annotated method should take in one parameter of type AbstractEvent or any of its subclasses.
 The priority of the event hook can be specified with the priority parameter, with a lower value indicating a higher priority.
 @author pvpb0t
 @since 3/12/2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHook {
    int priority() default 0;
}

