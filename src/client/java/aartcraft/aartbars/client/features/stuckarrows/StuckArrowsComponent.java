package aartcraft.aartbars.client.features.stuckarrows;

import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class StuckArrowsComponent extends BaseHUDComponent {
    private int arrowAppearTick = 0;
    private int lastArrowCount = 0;
    private static final int SHAKE_DURATION = 20;
    private final Random random = new Random();

    public StuckArrowsComponent() {
        super(0, 0); // Initialize with 0,0
    }

    @Override
    public void render(DrawContext context, int screenWidth, int screenHeight) {
        // Update position using provided screen dimensions
        this.x = screenWidth / 2 + 91;
        this.y = screenHeight - 39;
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null) return;

        int stuckarrows = player.getStuckArrowCount();

        if (stuckarrows != lastArrowCount) {
            arrowAppearTick = mc.inGameHud.getTicks();
            lastArrowCount = stuckarrows;
        }

        if (stuckarrows > 0) {
            drawStuckArrowsOverlay(context, stuckarrows, mc, x, y, alpha);
        }
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof StuckArrowsEvent stuckArrowsEvent) {
            if (!stuckArrowsEvent.isCanceled && stuckArrowsEvent.stuckarrows > 0) {
                drawStuckArrowsOverlay(stuckArrowsEvent, MinecraftClient.getInstance(), 1f);
            }
        }
    }

    public void drawStuckArrowsOverlay(StuckArrowsEvent event, MinecraftClient mc, float alpha) {
        drawStuckArrowsOverlay(event.context, event.stuckarrows, mc, event.x, event.y, alpha);
    }

    private void drawStuckArrowsOverlay(DrawContext context, int stuckarrows, MinecraftClient mc, int right, int top, float alpha) {
        int iconSize = 8;
        int spacing = 2;
        int iconsPerRow = 10;

        int rows = (int) Math.ceil((float) stuckarrows / iconsPerRow);
        int startX = right - (iconsPerRow * iconSize);
        int startY = top - (rows * (iconSize + spacing));

        enableAlpha(alpha);

        int ticks = mc.inGameHud.getTicks();

        for (int i = 0; i < stuckarrows; i++) {
            int row = i / iconsPerRow;
            int col = i % iconsPerRow;

            int x = startX + ((iconsPerRow - 1 - col) * iconSize);
            int y = startY + (row * (iconSize + spacing));

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
}
