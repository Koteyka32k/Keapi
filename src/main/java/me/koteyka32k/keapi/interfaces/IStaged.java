package me.koteyka32k.keapi.interfaces;


import me.koteyka32k.keapi.stage.Stage;

/**
 * An interface describing a staged event.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public interface IStaged {
    /**
     * Returns the stage.
     */
    Stage getStage();

    /**
     * Returns the event's preferred stage.
     */
    default Stage getPreferredStage() {
        return Stage.ON;
    }

    /**
     * Sets the stage.
     */
    void setStage(Stage stage);

    /**
     * Updates the stage.
     * @return Whether updating the stage succeeded.
     */
    default boolean  updateStage() {
        switch (getStage()) {
            case PRE:
                setStage(Stage.ON);
                return true;
            case ON:
                setStage(Stage.POST);
                return true;
            default:
                return false;
        }
    }
}
