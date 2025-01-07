package aartcraft.aartbars.client.features.thermometer;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.gui.DrawContext;

public class ThermometerEvent extends HUDOverlayEvent {
    public final float temperature;

    public ThermometerEvent(float temperature, int x, int y, DrawContext context) {
        super(x, y, context);
        this.temperature = temperature;
    }

    public static final Event<EventHandler<ThermometerEvent>> EVENT =
            EventHandler.createArrayBacked();
}
