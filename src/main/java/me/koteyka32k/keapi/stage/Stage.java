package me.koteyka32k.keapi.stage;


/**
 * An enum describing the stage of an event.
 *
 *
 * @author Koteyka32k
 * @since 1.0
 */
public enum Stage {
    /**
     * Before something.
     */
    PRE,

    /**
     * Right before or during something.
     */
    ON,

    /**
     * After something.
     */
    POST,

    /**
     * Any of the stages.
     */
    ANY,

    /**
     * Use the event's preferred stage.
     * This is the default.
     */
    PREFERRED
}