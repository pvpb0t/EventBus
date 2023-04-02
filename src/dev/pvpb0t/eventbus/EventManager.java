package dev.pvpb0t.eventbus;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * EventManager class for managing event subscriptions and handling event hooks.
 * Uses a ConcurrentHashMap to store the subscribers for thread-safe access.
 * Each event class is mapped to a list of DefaultListeners.
 * @author pvpb0t
 * @since 3/12/2023
 */
public class EventManager {

    // ConcurrentHashMap used to store subscribers for thread-safe access
    private final ConcurrentHashMap<Class<? extends AbstractEvent>, ArrayList<DefaultListener>> subscribers = new ConcurrentHashMap<>();

    /**
     * Subscribes an object to events that it has specified methods for.
     * Gets all declared methods of the object, checks for the presence of the EventHook annotation,
     * and adds a DefaultListener to the corresponding list of the event class in the subscribers map.
     * @param object the object to be subscribed to events.
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
                    subscribers.computeIfAbsent(eventClazz, k -> new ArrayList<>()).add(listener);
                    subscribers.get(eventClazz).sort(new Comparator<DefaultListener>() {
                        @Override
                        public int compare(DefaultListener o1, DefaultListener o2) {
                            return Integer.compare(o2.getPrio(), o1.getPrio());
                        }
                    });
                }
            }
        }
    }

    /**
     * Hooks an event to all its subscribed listeners.
     * Invokes the handle method of each DefaultListener that is subscribed to the event class of the given event.
     * @param event the event to be hooked to listeners.
     */
    public void hook(AbstractEvent event) {
        Class<? extends AbstractEvent> eventClass = event.getClass();
        if (subscribers.containsKey(eventClass)) {
            ArrayList<DefaultListener> listenersCopy = new ArrayList<>(subscribers.get(eventClass));
            for (DefaultListener listener : listenersCopy) {
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



   /**
    * Unsubscribes an object from all events that it has subscribed to.
    *
    * @param object the object to be unsubscribed from events.
    */
   public void unsubscribe(final Object object) {
       Class<?> clazz = object.getClass();
       for (Method method : clazz.getDeclaredMethods()) {
           if (method.isAnnotationPresent(EventHook.class)) {
               Class<?>[] parameterTypes = method.getParameterTypes();
               if (parameterTypes.length > 0 && AbstractEvent.class.isAssignableFrom(parameterTypes[0])) {
                   final Class<? extends AbstractEvent> eventClazz = (Class<? extends AbstractEvent>) method.getParameterTypes()[0];
                   // Get the list of listeners for the event class
                   ArrayList<DefaultListener> listeners = subscribers.get(eventClazz);
                   if (listeners != null) {
                       // Remove all listeners that have the object as their source
                       listeners.removeIf(listener -> listener.getSource() == object);
                       // If there are no more listeners for the event class, remove it from the map
                       if (listeners.isEmpty()) {
                           subscribers.remove(eventClazz);
                       }
                   }
               }
           }
       }
}









