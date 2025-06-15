package me.koteyka32k.keapi.interfaces;


import me.koteyka32k.keapi.stage.Stage;

/**
 * An interface describing a listener.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public interface IListener {
    /**
     * @return The event class the listener is listening to.
     */
    Class<?> getTarget();

    /**
     * @return The stage of the event that the listener is listening to.
     */
    Stage getStage();

    /**
     * @return The priority of the listener.
     */
    int getPriority();

    /**
     * @return Whether the listener ignores cancellation.
     */
    boolean ignoresCancellation();

    /**
     * Invokes the listeners's callback.
     *
     * @param event The event.
     */
     <T> void invokeCallback(T event);
}