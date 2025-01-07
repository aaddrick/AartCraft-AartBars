package aartcraft.aartbars.client;

import aartcraft.aartbars.AartBars;
import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBarsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("AartcraftArrowHUD");

    @Override
    public void onInitializeClient() {
        try {
            LOGGER.info("Initializing Aartcraft ArrowHUD client");

            // Register HUD render callback
            HudRenderCallback.EVENT.register((drawContext, tickDelta) -> HUDOverlayHandler.INSTANCE.onRender(drawContext));

            // Initialize the HUD overlay handler
            HUDOverlayHandler.init();

            // Register event handlers
            StuckArrowsEvent.EVENT.register((StuckArrowsEvent event) -> {
                if (!event.isCanceled) {
                    HUDOverlayHandler.INSTANCE.onStuckArrowsRender(event);
                }
            });

            LOGGER.info("Aartcraft AartBars client initialized successfully " + AartBars.MOD_ID);
        } catch (Exception e) {
            LOGGER.error("Failed to initialize Aartcraft ArrowHUD client", e);
            throw e;
        }
    }
}
