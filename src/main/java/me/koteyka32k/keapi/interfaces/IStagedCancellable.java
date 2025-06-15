package me.koteyka32k.keapi.interfaces;


/**
 * An interface describing a staged and cancellable event.
 * This is so that the event bus can know if an event
 * is both staged and cancellable.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public interface IStagedCancellable extends IStaged, ICancellable {

}
