package aartcraft.aartbars.api.handler;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Interface for handling events in the Aartcraft mod.
 * @param <IEvent> The type of event to handle
 */
public interface EventHandler<IEvent>
{
    /**
     * Creates a new array-backed event.
     * @param <T> The type of event
     * @return A new Event instance
     */
    static <T> Event<EventHandler<T>> createArrayBacked()
    {
        return EventFactory.createArrayBacked(EventHandler.class, listeners -> event -> {
            if (event == null) {
                throw new IllegalArgumentException("Event cannot be null");
            }
            
            for (EventHandler<T> listener : listeners) {
                try {
                    listener.interact(event);
                } catch (Exception e) {
                    // Log the error but continue processing other listeners
                    System.err.println("Error handling event: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Handles the given event.
     * @param event The event to handle
     */
    void interact(IEvent event);
}
