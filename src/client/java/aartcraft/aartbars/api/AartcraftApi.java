package aartcraft.aartbars.api;

/**
 * Used as an entrypoint in order to allow for integration with AppleSkin
 * without depending on AppleSkin at runtime.
 */
public interface AartcraftApi
{
    /**
     * Called at client-init in order for the implementer to register events with
     * the AppleSkin API ({@see squeek.appleskin.api.event})
     */
    void registerEvents();
}
