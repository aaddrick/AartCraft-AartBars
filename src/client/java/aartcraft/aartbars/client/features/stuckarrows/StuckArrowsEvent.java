package aartcraft.aartbars.client.features.stuckarrows;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;

public class StuckArrowsEvent extends HUDOverlayEvent {
    public final int stuckarrows;

    public StuckArrowsEvent(int stuckarrows, int x, int y, DrawContext context) {
        super(x, y, context);
        this.stuckarrows = stuckarrows;
    }

    public static final Event<EventHandler<StuckArrowsEvent>> EVENT =
            EventHandler.createArrayBacked();
}
