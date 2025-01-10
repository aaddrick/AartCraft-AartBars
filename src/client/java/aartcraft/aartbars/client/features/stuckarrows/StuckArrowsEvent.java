package aartcraft.aartbars.client.features.stuckarrows;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class StuckArrowsEvent extends HUDOverlayEvent {
    public final int stuckarrows;

    public StuckArrowsEvent(int stuckarrows, int x, int y, @NotNull DrawContext context) {
        super(x, y, context);
        this.stuckarrows = stuckarrows;
    }

    @ApiStatus.Internal
    public static final Event<EventHandler<StuckArrowsEvent>> EVENT =
            EventHandler.createArrayBacked();
}
