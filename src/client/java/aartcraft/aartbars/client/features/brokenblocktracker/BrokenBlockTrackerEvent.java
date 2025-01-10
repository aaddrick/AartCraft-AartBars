package aartcraft.aartbars.client.features.brokenblocktracker;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class BrokenBlockTrackerEvent extends HUDOverlayEvent {
    public final int blocksBroken;

    public BrokenBlockTrackerEvent(int blocksBroken, int x, int y, @NotNull DrawContext context) {
        super(x, y, context);
        this.blocksBroken = blocksBroken;
    }

    @ApiStatus.Internal
    public static final Event<EventHandler<BrokenBlockTrackerEvent>> EVENT =
            EventHandler.createArrayBacked();
}
