package aartcraft.aartbars.api.event;

import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;

public class HUDOverlayEvent
{
    /**
     * Event for rendering stuck arrows HUD overlay.
     * If cancelled, will stop all rendering of the stuck arrow meter.
     */
    public static class StuckArrows extends HUDOverlayEvent
    {
        public StuckArrows(int stuckarrows, int x, int y, DrawContext context)
        {
            super(x, y, context);
            this.stuckarrows = stuckarrows;
        }

        public final int stuckarrows;

        public static final Event<EventHandler<StuckArrows>> EVENT =
                EventHandler.createArrayBacked();
    }

    protected HUDOverlayEvent(int x, int y, DrawContext context)
    {
        this.x = x;
        this.y = y;
        this.context = context;

    }

    public final int x;
    public final int y;
    public final DrawContext context;
    public boolean isCanceled = false;
}