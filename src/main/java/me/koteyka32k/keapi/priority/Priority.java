package me.koteyka32k.keapi.priority;


/**
 * Default priorities for event listeners.
 * The higher the value, the more priority it has over other listeners.
 * THe lower the value, the less priority it has over other listeners.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public class Priority {
    public static final int LOWEST = 0;
    public static final int LOW = 500000;
    public static final int MEDIUM_LOW = 750000;
    public static final int NORMAL = 1000000;
    public static final int MEDIUM_HIGH = 1250000;
    public static final int HIGH = 1500000;
    public static final int HIGHEST = 2000000;
}