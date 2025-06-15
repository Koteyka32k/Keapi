package me.koteyka32k.keapi.listener;


import me.koteyka32k.keapi.interfaces.IListener;
import me.koteyka32k.keapi.stage.Stage;
import me.koteyka32k.keapi.subscribe.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A listener that uses reflection, this is for internal use by the
 * default implementation of the event bus.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public class ReflectionListener implements IListener {
    private final Object object;
    private final Method method;
    private final Class<?> target;
    private final Stage stage;
    private final int priority;
    private final boolean ignoresCancellation;

    /**
     * Internal use.
     *
     * @param object The object.
     * @param method The method.
     * @param target The target event class.
     * @param subscriber The subscriber annotation.
     */
    public ReflectionListener(Object object, Method method, Class<?> target, Subscribe subscriber) {
        this.object = object;
        this.method = method;
        this.target = target;
        this.stage = subscriber.stage();
        this.priority = subscriber.priority();
        this.ignoresCancellation = subscriber.ignoreCancellation();
        method.setAccessible(true);
    }

    @Override
    public void invokeCallback(Object event) {
        try {
            method.invoke(object, event);
        } catch (InvocationTargetException | IllegalAccessException ignored) {
            // It's not as if this will ever happen, unless you specify something like Pair<String, Long> which looks
            // exactly like Pair<IListener, IEventBus> to the event bus because Java type erasure.
        }
    }

    @Override
    public Class<?> getTarget() {
        return target;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean ignoresCancellation() {
        return ignoresCancellation;
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}