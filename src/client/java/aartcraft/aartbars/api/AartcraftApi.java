package aartcraft.aartbars.api;

import aartcraft.aartbars.client.components.HUDComponent;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;

/**
 * Used as an entrypoint in order to allow for integration with other mods.
 */
public interface AartcraftApi {
    /**
     * Called at client-init in order for the implementer to register events with
     * the Aartcraft API.
     */
    void registerEvents();

    /**
     * Registers a new HUD component.
     * @param component The HUD component to register
     */
    void registerComponent(HUDComponent component);

    /**
     * Registers a new HUD overlay event.
     * @param eventClass The class of the event to register
     * @param handler The event handler
     * @param <T> The type of event
     */
    <T extends HUDOverlayEvent> void registerEvent(Class<T> eventClass, EventHandler<T> handler);
}
