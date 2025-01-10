package aartcraft.aartbars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.loader.api.FabricLoader;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
        FabricLoader.getInstance().getConfigDir().toFile(),
        "aartbars.json"
    );

    public boolean showStuckArrows = true;
    public boolean showSpeedometer = true;
    public boolean showThermometer = true;
    public boolean showBrokenBlockTracker = true;
    
    // Offset configuration
    public int stuckArrowsX = 0;
    public int stuckArrowsY = 0;
    public int speedometerX = 0;
    public int speedometerY = 0;
    public int thermometerX = 0;
    public int thermometerY = 0;
    public int brokenBlockTrackerX = 0;
    public int brokenBlockTrackerY = 0;

    public static ModConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                AartBars.LOGGER.error("Failed to load config", e);
            }
        }
        return new ModConfig();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            AartBars.LOGGER.error("Failed to save config", e);
        }
    }
}
