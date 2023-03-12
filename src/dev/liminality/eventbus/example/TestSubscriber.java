package dev.liminality.eventbus.example;

import dev.liminality.eventbus.EventHook;

/**
 A class representing a subscriber for the TestEvent.
 This class contains an event hook method that is called when a TestEvent is fired.
 The event hook method logs a message to the console.
 The method is annotated with @EventHook and has a priority of 3.
 @author pvpb0t
 @since 3/12/2023
 */
public class TestSubscriber {

    /**
     Event hook method called when a TestEvent is fired.
     Logs a message to the console.
     @param e The TestEvent object fired.
     */
    @EventHook(priority = 3)
    public void onTestEvent(TestEvent e){
        System.out.println("event worked ig");
    }

}
