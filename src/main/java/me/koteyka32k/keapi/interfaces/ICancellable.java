package me.koteyka32k.keapi.interfaces;


/**
 * An interface describing a cancellable event.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public interface ICancellable {
    /**
     * @return Whether the event is cancelled or not.
     */
    boolean isCancelled();

    /**
     * @param cancelled New state.
     */
    void setCancelled(boolean cancelled);

    /**
     * Cancels the event.
     */
    default void cancel() {
        setCancelled(true);
    }
}
