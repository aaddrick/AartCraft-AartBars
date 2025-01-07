package aartcraft.aartbars.client.features.brokenblocktracker;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;

public class BrokenBlockTrackerEvent extends HUDOverlayEvent {
    public final int blocksBroken;

    public BrokenBlockTrackerEvent(int blocksBroken, int x, int y, DrawContext context) {
        super(x, y, context);
        this.blocksBroken = blocksBroken;
    }

    public static final Event<EventHandler<BrokenBlockTrackerEvent>> EVENT =
            EventHandler.createArrayBacked();
}
