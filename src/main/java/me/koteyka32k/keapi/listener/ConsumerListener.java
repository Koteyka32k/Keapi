package me.koteyka32k.keapi.listener;


import me.koteyka32k.keapi.interfaces.IListener;
import me.koteyka32k.keapi.priority.Priority;
import me.koteyka32k.keapi.stage.Stage;

import java.util.function.Consumer;

/**
 * A listener that uses a Consumer for invocation.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public class ConsumerListener<T> implements IListener {
    /**
     * A consumer which will be accepted upon invocation.
     */
    private final Consumer<T> consumer;
    private final Class<T> target;
    private final Stage stage;
    private final int priority;
    private final boolean ignoreCancellation;

    /**
     * The constructor, there are multiple variants so you can use the parameters just
     * like in the Subscribe annotation.
     *
     * @param target Target event class.
     * @param stage Stage of the event.
     * @param priority Priority of the listener.
     * @param ignoreCancellation Whether the listener ignores cancellation.
     * @param consumer Consumer function.
     */
    public ConsumerListener(Class<T> target, Stage stage, int priority, boolean ignoreCancellation, Consumer<T> consumer) {
        this.consumer = consumer;
        this.target = target;
        this.stage = stage;
        this.priority = priority;
        this.ignoreCancellation = ignoreCancellation;
    }

    public ConsumerListener(Class<T> target, Stage stage, int priority, Consumer<T> consumer) {
        this(target, stage, priority, false, consumer);
    }

    public ConsumerListener(Class<T> target, Stage stage, boolean ignoreCancellation, Consumer<T> consumer) {
        this(target, stage, Priority.NORMAL, ignoreCancellation, consumer);
    }

    public ConsumerListener(Class<T> target, Stage stage, Consumer<T> consumer) {
        this(target, stage, Priority.NORMAL, false, consumer);
    }

    public ConsumerListener(Class<T> target, int priority, boolean ignoreCancellation, Consumer<T> consumer) {
        this(target, Stage.PREFERRED, priority, ignoreCancellation, consumer);
    }

    public ConsumerListener(Class<T> target, int priority, Consumer<T> consumer) {
        this(target, Stage.PREFERRED, priority, false, consumer);
    }

    public ConsumerListener(Class<T> target, boolean ignoreCancellation, Consumer<T> consumer) {
        this(target, Stage.PREFERRED, Priority.NORMAL, ignoreCancellation, consumer);
    }

    public ConsumerListener(Class<T> target, Consumer<T> consumer) {
        this(target, Stage.PREFERRED, Priority.NORMAL, false, consumer);
    }

    @Override
    public Class<T> getTarget() {
        return target;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean ignoresCancellation() {
        return ignoreCancellation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T2> void invokeCallback(T2 event) {
        consumer.accept((T)event);
    }
}
