package me.koteyka32k.keapi.listener;


import me.koteyka32k.keapi.interfaces.IListener;
import me.koteyka32k.keapi.priority.Priority;
import me.koteyka32k.keapi.stage.Stage;

/**
 * An abstract listener that uses direct invocation.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public abstract class DirectListener<T> implements IListener {
    private final Class<T> target;
    private final Stage stage;
    private final int priority;
    private final boolean ignoreCancellation;

    /**
     * The constructor, there are multiple variants so you can use the parameters just
     * like in the Subscribe annotation.
     *
     *
     * @param target Target event class.
     * @param stage Stage of the event.
     * @param priority Priority of the listener.
     * @param ignoreCancellation Whether the listener ignores cancellation.
     */
    public DirectListener(Class<T> target, Stage stage, int priority, boolean ignoreCancellation) {
        this.target = target;
        this.stage = stage;
        this.priority = priority;
        this.ignoreCancellation = ignoreCancellation;
    }

    public DirectListener(Class<T> target, Stage stage, int priority) {
        this(target, stage, priority, false);
    }

    public DirectListener(Class<T> target, Stage stage, boolean ignoreCancellation) {
        this(target, stage, Priority.NORMAL, ignoreCancellation);
    }

    public DirectListener(Class<T> target, Stage stage) {
        this(target, stage, Priority.NORMAL, false);
    }

    public DirectListener(Class<T> target, int priority, boolean ignoreCancellation) {
        this(target, Stage.PREFERRED, priority, ignoreCancellation);
    }

    public DirectListener(Class<T> target, int priority) {
        this(target, Stage.PREFERRED, priority, false);
    }

    public DirectListener(Class<T> target, boolean ignoreCancellation) {
        this(target, Stage.PREFERRED, Priority.NORMAL, ignoreCancellation);
    }

    public DirectListener(Class<T> target) {
        this(target, Stage.PREFERRED, Priority.NORMAL, false);
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

    /**
     * The handler, it must be overridden.
     *
     * @param event The event.
     */
    public abstract void handle(T event);

    @Override
    @SuppressWarnings("unchecked")
    public <T2> void invokeCallback(T2 event) {
        handle((T)event);
    }
}
