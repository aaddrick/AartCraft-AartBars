package aartcraft.aartbars.client.components.stuckarrows;

import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

public class StuckArrowsEvent {
    public final int stuckarrows;
    public final int x;
    public final int y;
    public final DrawContext context;

    public StuckArrowsEvent(int stuckarrows, int x, int y, @NotNull DrawContext context) {
        this.stuckarrows = stuckarrows;
        this.x = x;
        this.y = y;
        this.context = context;
    }
}
