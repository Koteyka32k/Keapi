package me.koteyka32k.keapi.event;


import me.koteyka32k.keapi.interfaces.ICancellable;

/**
 * An abstract event which is cancellable.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public abstract class CancellableEvent extends Event implements ICancellable {
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}