package aartcraft.aartbars.api;

import aartcraft.aartbars.client.components.HUDComponent;
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
}
