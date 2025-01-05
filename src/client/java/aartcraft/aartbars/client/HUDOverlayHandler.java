package aartcraft.aartbars.client;

import aartcraft.aartbars.AartBars;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.helpers.TextureHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Random;

public class HUDOverlayHandler
{
    public static HUDOverlayHandler INSTANCE;

    private int arrowAppearTick = 0; // Tracks the game tick when arrows first appeared
    private int lastArrowCount = 0; // Tracks the previous arrow count
    private static final int SHAKE_DURATION = 20; // Duration of the shake effect in ticks (1 second)
    private final Random random = new Random();

    public static void init()
    {
        INSTANCE = new HUDOverlayHandler();
        // Add logging for ARROW_TEXTURE
        AartBars.LOGGER.info("Arrow texture path: {}", TextureHelper.ARROW_SPRITE);
        AartBars.LOGGER.info("Is namespace valid? " + Identifier.isNamespaceValid(AartBars.MOD_ID));
        AartBars.LOGGER.info("Is path valid? " + Identifier.isPathValid("textures/arrowsprite.png"));
        // Register the event listener
        HUDOverlayEvent.StuckArrows.EVENT.register(INSTANCE::onStuckArrowsRender);
    }

    /**
     * Called during HUD rendering to handle stuck arrows overlay.
     */
    public void onRender(DrawContext context)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null)
            return;

        int stuckarrows = player.getStuckArrowCount();

        // Check if the arrow count has changed
        if (stuckarrows != lastArrowCount)
        {
            arrowAppearTick = mc.inGameHud.getTicks(); // Record the current tick
            lastArrowCount = stuckarrows; // Update the last arrow count
        }

        int right = mc.getWindow().getScaledWidth() / 2 + 91; // Right side of the food bar
        int top = mc.getWindow().getScaledHeight() - 39; // Top of the food bar

        // Trigger the stuck arrows overlay event
        HUDOverlayEvent.StuckArrows renderEvent = new HUDOverlayEvent.StuckArrows(stuckarrows, right, top, context);
        HUDOverlayEvent.StuckArrows.EVENT.invoker().interact(renderEvent);

        // Draw arrow icons if the event is not canceled
        if (!renderEvent.isCanceled && renderEvent.stuckarrows > 0)
        {
            drawStuckArrowsOverlay(renderEvent, mc, 1f);
        }

    }

    public void onStuckArrowsRender(HUDOverlayEvent.StuckArrows event)
    {
        if (event.isCanceled || event.stuckarrows <= 0)
            return;

        MinecraftClient mc = MinecraftClient.getInstance();
        drawStuckArrowsOverlay(event, mc, 1f);
    }

    /**
     * Overload method to draw the stuck arrows overlay using the event object.
     */
    public void drawStuckArrowsOverlay(
            HUDOverlayEvent.StuckArrows event,
            MinecraftClient mc,
            float alpha)
    {

        drawStuckArrowsOverlay(
                event.context,
                event.stuckarrows,
                mc,
                event.x,
                event.y,
                alpha);
    }

    /**
     * Overload method to draw the stuck arrows overlay using individual parameters.
     */
    public void drawStuckArrowsOverlay(DrawContext context,
                                       int stuckarrows,
                                       MinecraftClient mc,
                                       int right,
                                       int top,
                                       float alpha) {

        int iconSize = 8;
        int spacing = 0;
        int iconsPerRow = 10;

        int rows = (int) Math.ceil((float) stuckarrows / iconsPerRow);
        // Start from left edge instead of right
        int startX = right;
        // Start from top and move upwards for new rows
        int startY = top - (rows * (iconSize + spacing));

        enableAlpha(alpha);

        int ticks = mc.inGameHud.getTicks();

        for (int i = 0; i < stuckarrows; i++) {
            int row = i / iconsPerRow;
            int col = i % iconsPerRow;

            // Calculate position left-to-right
            int x = startX - col * (iconSize + spacing);
            // Calculate position from top down (subtract row height)
            int y = startY + row * (iconSize + spacing);

            if (ticks - arrowAppearTick < SHAKE_DURATION) {
                x += random.nextInt(3) - 1;
                y += random.nextInt(3) - 1;
            }

            context.drawTexture(
                    RenderLayer::getGuiTextured,
                    TextureHelper.ARROW_SPRITE,
                    x, y,
                    0f, 0f,
                    8, 8,
                    iconSize, iconSize,
                    8, 8
                    );
        }
        disableAlpha();
    }

    private void enableAlpha(float alpha)
    {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }

    private void disableAlpha()
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
