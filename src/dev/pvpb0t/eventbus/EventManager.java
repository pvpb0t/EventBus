package dev.pvpb0t.eventbus;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 EventManager class for managing event subscriptions and handling event hooks.
 @author pvpb0t
 @since 3/12/2023
 */
public class EventManager {

    private final Map<Class<? extends AbstractEvent>, ArrayList<DefaultListener>> subscribers = new HashMap<>();

    /**
     Subscribes an object to events that it has specified methods for.
     @param object the object to be subscribed to events.
     */
    public void subscribe(final Object object) {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventHook.class)) {
                final EventHook eventHook = method.getAnnotation(EventHook.class);
                final int prio = eventHook.priority();
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length > 0 && AbstractEvent.class.isAssignableFrom(parameterTypes[0])) {
                    final Class<? extends AbstractEvent> eventClazz = (Class<? extends AbstractEvent>) method.getParameterTypes()[0];
                    DefaultListener listener = new DefaultListener(eventClazz, method);
                    listener.setPrio(prio);
                    listener.setSource(object);
                    if(subscribers.containsKey(eventClazz)){
                        if(!subscribers.get(eventClazz).contains(listener)){
                            subscribers.get(eventClazz).add(listener);
                            subscribers.get(eventClazz).sort(new Comparator<DefaultListener>() {
                                @Override
                                public int compare(DefaultListener o1, DefaultListener o2) {
                                    return Integer.compare(o2.getPrio(), o1.getPrio());
                                }
                            });
                        }
                    }else{
                        ArrayList<DefaultListener> listeners = new ArrayList<>();
                        listeners.add(listener);
                        this.subscribers.put(eventClazz, listeners);
                    }
                }
            }
        }
    }


    /**
     Hooks an event to all its subscribed listeners.
     @param event the event to be hooked to listeners.
     */
    public void hook(AbstractEvent event) {
        if(event.isCancelled())
            return;
        Class<? extends AbstractEvent> eventClass = event.getClass();
        if (subscribers.containsKey(eventClass)) {
            for (DefaultListener listener : subscribers.get(eventClass)) {
                try {
                    MethodHandles.Lookup lookup = MethodHandles.lookup();
                    MethodHandle handle = lookup.unreflect(listener.getTarget());
                    handle.invoke(listener.getSource(), event);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }












}
