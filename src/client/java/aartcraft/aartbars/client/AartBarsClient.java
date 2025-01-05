package aartcraft.aartbars.client;

import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBarsClient implements ClientModInitializer {
    // Add this logger field
    private static final Logger LOGGER = LoggerFactory.getLogger("AartBarsClient");

    @Override
    public void onInitializeClient() {
        try {
            LOGGER.info("Initializing Aartcraft ArrowHUD client");

            // Register HUD render callback
            HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
                HUDOverlayHandler.INSTANCE.onRender(drawContext);
            });

            // Initialize the HUD overlay handler
            HUDOverlayHandler.init();

            // Register event handlers
            HUDOverlayEvent.StuckArrows.EVENT.register((event) -> {
                if (!event.isCanceled) {
                    HUDOverlayHandler.INSTANCE.drawStuckArrowsOverlay(event, MinecraftClient.getInstance(), 1f);
                }
            });

            LOGGER.info("Aartcraft ArrowHUD client initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize Aartcraft ArrowHUD client", e);
            throw e;
        }
    }
}
