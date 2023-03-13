package dev.pvpb0t.eventbus;

/**
 An abstract class for events that can be listened to and cancelled.
 Subclasses should override the appropriate methods to define the event's behavior.
 @author pvpb0t
 @since 3/12/2023
 */
public abstract class AbstractEvent {

    /**
     Indicates whether the event has been cancelled.
     @return true if the event is cancelled, false otherwise.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     Sets the cancelled state of the event.
     @param way the new cancelled state of the event.
     */
    public void setCancelled(boolean way) {
        this.cancelled = way;
    }

    /**
     Represents the cancelled state of the event.
     */
    private boolean cancelled = false;

}

