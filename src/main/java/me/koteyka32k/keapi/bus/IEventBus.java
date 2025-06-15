package me.koteyka32k.keapi.bus;


import me.koteyka32k.keapi.interfaces.ICancellable;
import me.koteyka32k.keapi.interfaces.IStaged;
import me.koteyka32k.keapi.interfaces.IStagedCancellable;
import me.koteyka32k.keapi.interfaces.IListener;

/**
 * An interface describing an event bus.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public interface IEventBus {
    /**
     * Subscribes an object.
     */
    void subscribe(Object listener);

    /**
     * Subscribes an individual listener.
     */
    void subscribe(IListener listener);

    /**
     * Unsubscribes an object.
     */
    void unsubscribe(Object listener);

    /**
     * Unsubscribes an individual listener.
     */
    void unsubscribe(IListener listener);

    /**
     * Posts an event.
     */
    <T> T post(T event);

    /**
     * Posts a cancellable event.
     */
    <T extends ICancellable> T post(T event);

    /**
     * Posts a staged event.
     */
    <T extends IStaged> T post(T event);

    /**
     * Posts a staged and cancellable event.
     */
    <T extends IStagedCancellable> T post(T event);

}
