package aartcraft.aartbars.api.handler;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;

/**
 * Interface for handling events in the Aartcraft mod.
 * @param <IEvent> The type of event to handle
 */
@ApiStatus.Internal
public interface EventHandler<IEvent>
{
    /**
     * Creates a new array-backed event.
     * @param <T> The type of event
     * @return A new Event instance
     */
    @ApiStatus.Internal
    static <T> Event<EventHandler<T>> createArrayBacked()
    {
        return EventFactory.createArrayBacked(EventHandler.class, listeners -> event -> {
            Objects.requireNonNull(event, "Event cannot be null");
            
            for (EventHandler<T> listener : listeners) {
                try {
                    listener.interact(event);
                } catch (Exception e) {
                    // Log the error but continue processing other listeners
                    AartBarsClient.LOGGER.error("Error handling event", e);
                }
            }
        });
    }

    /**
     * Handles the given event.
     * @param event The event to handle
     */
    void interact(@NotNull IEvent event);
}
