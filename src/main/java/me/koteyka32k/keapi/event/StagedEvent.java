package me.koteyka32k.keapi.event;


import me.koteyka32k.keapi.interfaces.IStaged;
import me.koteyka32k.keapi.stage.Stage;

/**
 * An abstract event which is staged.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public abstract class StagedEvent extends Event implements IStaged {
    private Stage stage = getPreferredStage();

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
