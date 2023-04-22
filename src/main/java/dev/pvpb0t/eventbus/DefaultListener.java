package dev.pvpb0t.eventbus;

import java.lang.reflect.Method;

/**
 A listener class that contains information about the event it listens to, its target method, its priority, and its source object.
 The listener can be constructed with a specified event, target method, and priority, or just an event and a target method.
 The priority defaults to 1 if not specified.
 The source object can be set through the setSource method.
 The target method can be retrieved with the getTarget method.
 The event class can be retrieved with the getEvent method.
 The priority can be retrieved and set with the getPrio and setPrio methods, respectively.
 The source object can be retrieved with the getSource method.
 @author pvpb0t
 @since 3/12/2023
 */
public class DefaultListener{
    private Class<? extends AbstractEvent> event;
    private Method target;
    private Integer priority = 1;
    private Object source;

    /**
     Returns the source object of this listener.
     @return the source object of this listener
     */
    public Object getSource() {
        return source;
    }

    /**
     Sets the source object of this listener.
     @param source the new source object of this listener
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     Returns the priority of this listener.
     @return the priority of this listener
     */
    public Integer getPrio() {
        return priority;
    }

    /**
     Sets the priority of this listener.
     @param prio the new priority of this listener
     */
    public void setPrio(Integer prio) {
        this.priority = prio;
    }

    /**
     Constructs a new DefaultListener with the specified event, target method, and priority.
     @param event the event class that this listener listens to
     @param target the target method that this listener calls when the event is fired
     @param priority the priority of this listener
     */
    public DefaultListener(Class<? extends AbstractEvent> event, Method target, int priority) {
        this.event = event;
        this.target = target;
        this.priority = priority;
    }

    /**
     Constructs a new DefaultListener with the specified event and target method.
     The priority is set to 1 by default.
     @param event the event class that this listener listens to
     @param target the target method that this listener calls when the event is fired
     */
    public DefaultListener(Class<? extends AbstractEvent> event, Method target) {
        this.event = event;
        this.target = target;
    }

    /**
     Sets the event class that this listener listens to.
     @param event the new event class of this listener
     */
    public void setEvent(Class<? extends AbstractEvent> event) {
        this.event = event;
    }

    /**
     Returns the target method that this listener calls when the event is fired.
     @return the target method of this listener
     */
    public Method getTarget() {
        return target;
    }

    /**
     Sets the target method that this listener calls when the event is fired.
     @param target the new target method of this listener
     */
    public void setTarget(Method target) {
        this.target = target;
    }

    /**
     Returns the event class that this listener listens to.
     @return the event class of this listener
     */
    public Class<? extends AbstractEvent> getEvent() {
        return event;
    }
}
