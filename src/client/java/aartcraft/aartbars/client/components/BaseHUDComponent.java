package aartcraft.aartbars.client.components;

import aartcraft.aartbars.ModConfig;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

/**
 * Base class for HUD components.
 */
public abstract class BaseHUDComponent implements HUDComponent {
    protected int x;
    protected int y;
    protected float alpha = 1.0f;

    protected final ModConfig config;
    
    /**
     * Creates a new BaseHUDComponent.
     *
     * @param x the x position
     * @param y the y position
     * @param config the mod configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public BaseHUDComponent(int x, int y, @NotNull ModConfig config) {
        this.x = x;
        this.y = y;
        this.config = Objects.requireNonNull(config, "ModConfig cannot be null");
    }


    /**
     * Enables alpha blending for rendering.
     *
     * @param alpha the alpha value
     */
    protected void enableAlpha(float alpha) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Disables alpha blending for rendering.
     */
    protected void disableAlpha() {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
