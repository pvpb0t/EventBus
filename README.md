# EventBus

## How is this eventbus different?

This event bus implementation is different from others as it avoids using reflection methods like Method.invoke() and instead uses statically defined method references. This means that the event bus does not rely on the JVM performing method lookups at runtime, resulting in faster and more efficient event handling. Additionally, using statically defined method references improves code safety and readability as the event listener methods are explicitly defined in the subscriber classes.

## Getting Started:
To use the EventBus, you will need to create an instance of the EventManager class. This class will manage all of the events and subscribers in your application. Here is an example of how to create an instance of the EventManager class:
```java
EventManager eventManager = new EventManager();
```

## Subscribing to Events:
After creating an instance of the EventManager class, you can subscribe to events using the subscribe() method. This method takes a subscriber object as a parameter. A subscriber object is an instance of a class that contains methods that will be called when an event is fired. These methods must have a single parameter that corresponds to the event object being fired. Here is an example of how to create a subscriber object:
```java
public class TestSubscriber {

	@EventHook(priority = 3)
	public void onTestEvent(TestEvent e){
		Liminality.LOGGER.info("event worked ig");
	}
}
```

The onTestEvent() method in this example will be called when a TestEvent object is fired. The @EventHook annotation on this method specifies the priority of this subscriber (in this case, 3). Higher priority subscribers will be called before lower priority subscribers.

To subscribe to events using the subscriber object, simply call the subscribe() method on the EventManager object and pass in the subscriber object:
```java
eventManager.subscribe(new TestSubscriber());
```

## Firing Events:
To fire an event, you will need to create an instance of the event object and call the hook() method on the EventManager object. Here is an example of how to create a TestEvent object and fire it:
```java
TestEvent testEvent = new TestEvent();
eventManager.hook(testEvent);
```
When the hook() method is called, the EventBus will call all of the subscriber methods that are subscribed to the TestEvent object in order of priority.

## Cancelling Events:
Events can be cancelled by setting the "cancelled" flag to true in the event object. This can be done by calling the setCancelled() method on the event object. Here is an example of how to cancel a TestEvent object:
```java
TestEvent testEvent = new TestEvent();
testEvent.setCancelled(true);
```
When an event is cancelled, the EventBus will stop calling subscriber methods for that event.

### Credits:

Thanks alot oragejuice for explaining how nukerbus worked while high asl and Hexception for inspiration
