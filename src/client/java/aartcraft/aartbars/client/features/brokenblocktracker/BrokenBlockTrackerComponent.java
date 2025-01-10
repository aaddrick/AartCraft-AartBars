package aartcraft.aartbars.client.features.brokenblocktracker;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

/**
 * HUD component that displays the number of blocks broken by the player.
 */
public final class BrokenBlockTrackerComponent extends BaseHUDComponent {
    private int blocksBroken = 0;

    /**
     * Creates a new BrokenBlockTrackerComponent.
     *
     * @param config the mod configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public BrokenBlockTrackerComponent(@NotNull ModConfig config) {
        super(0, 0, config);
    }

    @Override
    public void render(@NotNull DrawContext context, int screenWidth, int screenHeight) {
        Objects.requireNonNull(context, "DrawContext cannot be null");
        
        if (!AartBarsClient.config.showBrokenBlockTracker) return;
        
        this.x = screenWidth / 2 + 93 + config.brokenBlockTrackerX;
        this.y = screenHeight - 20 + config.brokenBlockTrackerY;
        
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.player == null || mc.textRenderer == null) return;

        String text = "Blocks Broken: " + blocksBroken;
        context.drawTextWithShadow(
            mc.textRenderer,
            Text.of(text),
            x, y,
            0xFFFFFF
        );
    }

    @Override
    public void handleEvent(@NotNull HUDOverlayEvent event) {
        Objects.requireNonNull(event, "HUDOverlayEvent cannot be null");
        
        if (event instanceof BrokenBlockTrackerEvent trackerEvent) {
            this.blocksBroken = trackerEvent.blocksBroken;
        }
    }
}
