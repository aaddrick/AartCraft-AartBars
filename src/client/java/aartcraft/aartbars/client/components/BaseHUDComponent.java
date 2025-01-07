package aartcraft.aartbars.client.components;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.gui.DrawContext;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

public abstract class BaseHUDComponent implements HUDComponent {
    protected int x;
    protected int y;
    protected float alpha = 1.0f;

    public BaseHUDComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        // Default implementation does nothing
    }

    protected void enableAlpha(float alpha) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }

    protected void disableAlpha() {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
