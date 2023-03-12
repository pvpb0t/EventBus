package dev.liminality.eventbus.example;

import dev.liminality.eventbus.EventManager;

public class Example {
    private static EventManager eventManager = new EventManager();
    public static void main(String[] args){
        eventManager.subscribe(new TestSubscriber());

        eventManager.hook(new TestEvent());
    }

}
