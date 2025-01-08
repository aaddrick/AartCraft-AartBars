package aartcraft.aartbars;

import aartcraft.aartbars.client.gui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBars implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "aartbars";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig config;
    
    private static KeyBinding configKeyBinding;

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Aart!");
        config = ModConfig.load();
    }

    @Override
    public void onInitializeClient() {
        // Register key binding (defaults to unbound)
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aartbars.config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN, // No default key
            "category.aartbars.main"
        ));
        
        // Register client tick event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new ConfigScreen(client.currentScreen));
                }
            }
        });
    }
}
