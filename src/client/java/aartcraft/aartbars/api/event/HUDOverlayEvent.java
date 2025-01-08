package aartcraft.aartbars.api.event;

import net.minecraft.client.gui.DrawContext;

public class HUDOverlayEvent {
    public final int x;
    public final int y;
    public final DrawContext context;
    public boolean isCanceled = false;

    protected HUDOverlayEvent(int x, int y, DrawContext context) {
        this.x = x;
        this.y = y;
        this.context = context;
    }
}
