package aartcraft.aartbars;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AartBars implements ModInitializer {
    public static final String MOD_ID = "aartbars";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Aart!");
    }
}
