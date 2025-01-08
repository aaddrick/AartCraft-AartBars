package aartcraft.aartbars.client;

import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsEvent;
import aartcraft.aartbars.client.gui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBarsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("AartcraftArrowHUD");
    private static KeyBinding configKeyBinding;

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

            // Register key binding for config screen
            configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aartbars.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // No default key
                "category.aartbars.main"
            ));

            // Register client tick event for key binding
            ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTick);

            LOGGER.info("Aartcraft AartBars client initialized successfully " + AartBars.MOD_ID);
        } catch (Exception e) {
            LOGGER.error("Failed to initialize Aartcraft ArrowHUD client", e);
            throw e;
        }
    }

    private void handleClientTick(MinecraftClient client) {
        while (configKeyBinding.wasPressed()) {
            if (client.player != null && client.currentScreen == null) {
                client.setScreen(new ConfigScreen(client.currentScreen, AartBars.config));
            }
        }
    }
}
