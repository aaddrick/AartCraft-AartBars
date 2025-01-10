package aartcraft.aartbars.api.event;

import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;

/**
 * Base class for HUD overlay events.
 */
@ApiStatus.Experimental
public class HUDOverlayEvent {
    public final int x;
    public final int y;
    @NotNull
    public final DrawContext context;
    public boolean isCanceled = false;

    /**
     * Creates a new HUDOverlayEvent.
     *
     * @param x the x position
     * @param y the y position
     * @param context the draw context, must not be null
     * @throws NullPointerException if context is null
     */
    public HUDOverlayEvent(int x, int y, @NotNull DrawContext context) {
        this.x = x;
        this.y = y;
        this.context = Objects.requireNonNull(context, "DrawContext cannot be null");
    }

    /**
     * Checks if the event is canceled.
     *
     * @return true if the event is canceled
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * Sets the canceled state of the event.
     *
     * @param canceled the new canceled state
     */
    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }
}
