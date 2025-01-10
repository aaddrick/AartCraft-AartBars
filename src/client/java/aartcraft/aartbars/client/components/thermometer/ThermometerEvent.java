package aartcraft.aartbars.client.components.thermometer;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class ThermometerEvent extends HUDOverlayEvent {
    public final float temperature;

    public ThermometerEvent(float temperature, int x, int y, @NotNull DrawContext context) {
        super(x, y, context);
        this.temperature = temperature;
    }

    @ApiStatus.Internal
    public static final Event<EventHandler<ThermometerEvent>> EVENT =
            EventHandler.createArrayBacked();
}
