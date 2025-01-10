package aartcraft.aartbars.client;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.client.gui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.ApiStatus;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBarsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("AartcraftArrowHUD");
    private static KeyBinding configKeyBinding;
    public static ModConfig config;

    @Override
    public void onInitializeClient() {
        try {
            LOGGER.info("Initializing Aartcraft ArrowHUD client");

            // Load and validate config
            config = ModConfig.load();
            try {
                config.validate();
            } catch (IllegalArgumentException e) {
                LOGGER.error("Invalid configuration values: {}", e.getMessage());
                config = new ModConfig(); // Fallback to default config
            }

            // Register HUD render callback
            HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
                try {
                    HUDOverlayHandler.INSTANCE.onRender(drawContext);
                } catch (Exception e) {
                    LOGGER.error("Error during HUD rendering", e);
                }
            });

            // Initialize the HUD overlay handler
            HUDOverlayHandler.init();

            // Register all event handlers
            registerEventHandlers();

            // Register key binding for config screen
            configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aartbars.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // No default key
                "category.aartbars.main"
            ));

            // Register client tick event for key binding
            ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTick);

            LOGGER.info("Aartcraft AartBars client initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize Aartcraft ArrowHUD client", e);
            throw e;
        }
    }

    @ApiStatus.Internal
    private void registerEventHandlers() {
        // Event handlers are now managed by Fabric's event system
    }

    private void handleClientTick(MinecraftClient client) {
        try {
            while (configKeyBinding.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new ConfigScreen(client.currentScreen, config));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during client tick handling", e);
        }
    }
}
