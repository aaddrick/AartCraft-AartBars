package aartcraft.aartbars.client.components;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.gui.DrawContext;

public interface HUDComponent {
    void render(DrawContext context, int screenWidth, int screenHeight);
    void handleEvent(HUDOverlayEvent event);
}
