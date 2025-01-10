package aartcraft.aartbars.client.components;

import net.minecraft.client.gui.DrawContext;

public interface HUDComponent {
    void render(DrawContext context, int screenWidth, int screenHeight);
}
