package aartcraft.aartbars.client.components.stuckarrows;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.Random;

/**
 * HUD component that displays the number of arrows stuck in the player.
 */
@ApiStatus.Experimental
public final class StuckArrowsComponent extends BaseHUDComponent {
    /**
     * HUD component that displays the number of arrows stuck in the player.
     * <p>
     * This component tracks the number of arrows embedded in the player
     * and displays them using a graphical representation.
     * <p>
     * <b>Experimental feature</b>, may be removed or changed without further notice.
     */
    private static final int SHAKE_DURATION = 20; // Duration of the shake animation in ticks
    private static final int ICON_SIZE = 8; // Size of each arrow icon
    private static final int SPACING = 2; // Spacing between arrow icons
    private static final int ICONS_PER_ROW = 10; // Number of arrows per row

    private int arrowAppearTick = 0; // Tracks when the arrow count last changed
    private int lastArrowCount = 0; // Tracks the last known arrow count
    private final Random random = new Random(); // Used for shake animation

    /**
     * Creates a new StuckArrowsComponent.
     *
     * @param config the mod configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public StuckArrowsComponent(@NotNull ModConfig config) {
        super(0, 0, config);
    }

    @Override
    public void render(@NotNull DrawContext context, int screenWidth, int screenHeight) {
        Objects.requireNonNull(context, "DrawContext cannot be null");

        if (!AartBarsClient.config.showStuckArrows) return;

        // Update position using config offsets
        this.x = screenWidth / 2 + 91 + config.stuckArrowsX;
        this.y = screenHeight - 39 + config.stuckArrowsY;

        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null) return;

        int stuckArrows = player.getStuckArrowCount();

        // Update animation state if arrow count changes
        if (stuckArrows != lastArrowCount) {
            arrowAppearTick = mc.inGameHud.getTicks();
            lastArrowCount = stuckArrows;
        }

        // Render arrows if any are stuck
        if (stuckArrows > 0) {
            drawStuckArrowsOverlay(context, stuckArrows, mc, x, y, alpha);
        }
    }

    @Override
    public void handleEvent(@NotNull HUDOverlayEvent event) {
        Objects.requireNonNull(event, "HUDOverlayEvent cannot be null");

        if (event instanceof StuckArrowsEvent stuckArrowsEvent) {
            if (!stuckArrowsEvent.isCanceled && stuckArrowsEvent.stuckarrows > 0) {
                drawStuckArrowsOverlay(stuckArrowsEvent, MinecraftClient.getInstance(), 1f);
            }
        }
    }

    /**
     * Draws the stuck arrows overlay.
     *
     * @param event the StuckArrowsEvent containing rendering data
     * @param mc the Minecraft client instance
     * @param alpha the alpha value for transparency
     */
    private void drawStuckArrowsOverlay(@NotNull StuckArrowsEvent event, @NotNull MinecraftClient mc, float alpha) {
        Objects.requireNonNull(event, "StuckArrowsEvent cannot be null");
        Objects.requireNonNull(mc, "MinecraftClient cannot be null");

        drawStuckArrowsOverlay(event.context, event.stuckarrows, mc, event.x, event.y, alpha);
    }

    /**
     * Draws the stuck arrows overlay.
     *
     * @param context the draw context, must not be null
     * @param stuckArrows the number of stuck arrows
     * @param mc the Minecraft client instance
     * @param right the right position of the overlay
     * @param top the top position of the overlay
     * @param alpha the alpha value for transparency
     */
    private void drawStuckArrowsOverlay(@NotNull DrawContext context, int stuckArrows, @NotNull MinecraftClient mc, int right, int top, float alpha) {
        Objects.requireNonNull(context, "DrawContext cannot be null");
        Objects.requireNonNull(mc, "MinecraftClient cannot be null");

        int rows = (int) Math.ceil((float) stuckArrows / ICONS_PER_ROW);
        int startX = right - (ICONS_PER_ROW * ICON_SIZE);
        int startY = top - (rows * (ICON_SIZE + SPACING));

        enableAlpha(alpha);

        int ticks = mc.inGameHud.getTicks();

        for (int i = 0; i < stuckArrows; i++) {
            int row = i / ICONS_PER_ROW;
            int col = i % ICONS_PER_ROW;

            int x = startX + ((ICONS_PER_ROW - 1 - col) * ICON_SIZE);
            int y = startY + (row * (ICON_SIZE + SPACING));

            // Apply shake animation if recently updated
            if (ticks - arrowAppearTick < SHAKE_DURATION) {
                x += random.nextInt(3) - 1;
                y += random.nextInt(3) - 1;
            }

            context.drawTexture(
                RenderLayer::getGuiTextured,
                TextureHelper.ARROW_SPRITE,
                x, y,
                0f, 0f,
                ICON_SIZE, ICON_SIZE,
                ICON_SIZE, ICON_SIZE,
                ICON_SIZE, ICON_SIZE
            );
        }
        disableAlpha();
    }
}
