package aartcraft.aartbars.client.features.speedometer;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class SpeedometerEvent extends HUDOverlayEvent {
    public final float speed;
    public final float rotation;

    public SpeedometerEvent(float speed, float rotation, int x, int y, @NotNull DrawContext context) {
        super(x, y, context);
        this.speed = speed;
        this.rotation = rotation;
    }

    @ApiStatus.Internal
    public static final Event<EventHandler<SpeedometerEvent>> EVENT =
            EventHandler.createArrayBacked();
}
