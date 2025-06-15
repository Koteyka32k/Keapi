package me.koteyka32k.keapi.bus;

import me.koteyka32k.keapi.interfaces.ICancellable;
import me.koteyka32k.keapi.interfaces.IStaged;
import me.koteyka32k.keapi.interfaces.IStagedCancellable;
import me.koteyka32k.keapi.interfaces.IListener;
import me.koteyka32k.keapi.listener.ReflectionListener;
import me.koteyka32k.keapi.stage.Stage;
import me.koteyka32k.keapi.subscribe.Subscribe;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Default implementation of the event bus.
 *
 * @author Koteyka32k
 * @since 1.0
 */
public class EventBus implements IEventBus {
    private final Map<Class<?>, List<IListener>> listenerMap = new IdentityHashMap<>();
    private final Map<Object, List<IListener>> cachedListenerMap = new IdentityHashMap<>();

    @Override
    public void subscribe(Object subscriber) {
        List<IListener> listeners = getSubscribers(subscriber);
        IListener listener;
        for (int i = -1; ++i < listeners.size(); ) {
            listener = listeners.get(i);
            listenerMap.get(listener.getTarget()).add(listener);
        }
    }

    @Override
    public void subscribe(IListener subscriber) {
        List<IListener> listeners = listenerMap.get(subscriber.getTarget());
        if (listeners != null) {
            listeners.add(subscriber);
        } else {
            listeners = new PrioritizedSubscriberList();
            listeners.add(subscriber);
            listenerMap.put(subscriber.getTarget(), listeners);
        }
    }

    @Override
    public void unsubscribe(Object subscriber) {
        List<IListener> listeners = getSubscribers(subscriber);
        IListener listener1;
        for (int i = -1; ++i < listeners.size(); ) {
            listener1 = listeners.get(i);
            listenerMap.get(listener1.getTarget()).remove(listener1);
        }
    }

    @Override
    public void unsubscribe(IListener subscriber) {
        List<IListener> listeners = listenerMap.get(subscriber.getTarget());
        if (listeners != null) {
            listeners.remove(subscriber);
        }
    }

    public <T> T post(T event) {
        List<IListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            for (int i = -1; ++i < listeners.size();) {
                listeners.get(i).invokeCallback(event);
            }
        }

        return event;
    }

    @Override
    public <T extends IStaged> T post(T event) {
        List<IListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            IListener info;
            for (int i = -1; ++i < listeners.size();) {
                info = listeners.get(i);
                if (info.getStage() != Stage.PREFERRED) {
                    if (info.getStage() != Stage.ANY) {
                        if (info.getStage() != event.getStage()) {
                            continue;
                        }
                    }
                } else {
                    if (event.getStage() != event.getPreferredStage()) {
                        continue;
                    }
                }
                info.invokeCallback(event);
            }
        }

        return event;
    }

    @Override
    public <T extends IStagedCancellable> T post(T event) {
        List<IListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            IListener info;
            for (int i = -1; ++i < listeners.size();) {
                info = listeners.get(i);
                if (event.isCancelled() && !info.ignoresCancellation()) {
                    continue;
                }
                if (info.getStage() != Stage.PREFERRED) {
                    if (info.getStage() != Stage.ANY) {
                        if (info.getStage() != event.getStage()) {
                            continue;
                        }
                    }
                } else {
                    if (event.getStage() != event.getPreferredStage()) {
                        continue;
                    }
                }
                info.invokeCallback(event);
            }
        }

        return event;
    }

    public <T extends ICancellable> T post(T event) {
        List<IListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            IListener info;
            for (int i = -1; ++i < listeners.size();) {
                info = listeners.get(i);
                if (event.isCancelled() && !info.ignoresCancellation()) {
                    continue;
                }
                info.invokeCallback(event);
            }
        }

        return event;
    }

    private List<IListener> getSubscribers(Object object) {
        List<IListener> listeners = cachedListenerMap.get(object);
        if (listeners == null) {
            // TODO: Add support for finding listeners in super classes.
            listeners = new ArrayList<>();
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (isMethodGood(method)) {
                    Class<?> target = method.getParameterTypes()[0];
                    if (!listenerMap.containsKey(target)) {
                        listenerMap.put(target, new PrioritizedSubscriberList());
                    }
                    listeners.add(new ReflectionListener(object, method, target, method.getAnnotation(Subscribe.class)));
                }
            }
            cachedListenerMap.put(object, listeners);
        }

        return listeners;
    }

    private boolean isMethodGood(Method method) {
        return method.getParameterCount() == 1 && method.isAnnotationPresent(Subscribe.class);
    }

    /**
     * Priority list.
     *
     * @author Koteyka32k
     * @since 1.0
     */
    private static final class PrioritizedSubscriberList extends ArrayList<IListener> {
        @Override
        public boolean add(IListener listener) {
            int index = -1;
            int size = size();
            while (++index  < size) {
                if (listener.getPriority() > get(index).getPriority()) {
                    break;
                }
            }

            add(index, listener);
            return true;
        }
    }
}