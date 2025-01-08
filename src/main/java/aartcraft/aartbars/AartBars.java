package aartcraft.aartbars;

import aartcraft.aartbars.client.gui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBars implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "aartbars";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig config;
    
    @Override
    public void onInitialize() {
        LOGGER.info("Hello Aart!");
        config = ModConfig.load();
    }

    @Override
    public void onInitializeClient() {
        // Client initialization handled in AartBarsClient
    }
}
