package aartcraft.aartbars.client.features.brokenblocktracker;

import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class BrokenBlockTrackerComponent extends BaseHUDComponent {
    private int blocksBroken = 0;

    public BrokenBlockTrackerComponent() {
        super(0, 0);
    }

    @Override
    public void render(DrawContext context, int screenWidth, int screenHeight) {
        if (!AartBarsClient.config.showBrokenBlockTracker) return;
        this.x = screenWidth / 2 + 93; // Left side position
        this.y = screenHeight - 20; // Bottom position
        
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        // Draw the text
        String text = "Blocks Broken: " + blocksBroken;
        context.drawTextWithShadow(
            mc.textRenderer,
            Text.of(text),
            x, y,
            0xFFFFFF
        );
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof BrokenBlockTrackerEvent trackerEvent) {
            this.blocksBroken = trackerEvent.blocksBroken;
        }
    }
}
