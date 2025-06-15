package me.koteyka32k.keapi.subscribe;


import me.koteyka32k.keapi.priority.Priority;
import me.koteyka32k.keapi.stage.Stage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation telling the event bus that this method
 * is listening. You can specify the stage of when you want
 * the method to be invoked, the priority of the listener,
 * and whether it ignores event cancellation. Ignoring
 * event cancellation is mainly used to monitor values.
 * <p>
 * A thing to note is that if you specify the stage to be Stage.PRE
 * and the target event does not implement IStaged, it will still
 * invoke the method, same thing with ignoreCancellation.
 *
 * @author Koteyka32k
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    /**
     * The stage of the event that the subscriber is listening to.
     * By default, it is the event's preferred stage.
     */
    Stage stage() default Stage.PREFERRED;

    /**
     * The priority of the listener.
     */
    int priority() default Priority.NORMAL;

    /**
     * Whether the listener ignores cancellation.
     */
    boolean ignoreCancellation() default false;
}