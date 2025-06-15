package me.koteyka32k.keapi.event;


import me.koteyka32k.keapi.interfaces.IStagedCancellable;
import me.koteyka32k.keapi.stage.Stage;

/**
 * An abstract event which is both staged and cancellable.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public abstract class StagedCancellableEvent extends Event implements IStagedCancellable {
    private Stage stage = getPreferredStage();
    private boolean cancelled;

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}