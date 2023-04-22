package dev.pvpb0t.eventbus;

import dev.pvpb0t.eventbus.EventManager;
import org.junit.jupiter.api.Test;

public class Example {
    private static EventManager eventManager = new EventManager();
    @Test
    public static void runTest(){
        eventManager.subscribe(new TestSubscriber());
        eventManager.hook(new TestEvent());
    }

}
