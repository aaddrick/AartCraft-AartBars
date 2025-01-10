package aartcraft.aartbars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration class for AartBars mod settings.
 */
public final class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
        FabricLoader.getInstance().getConfigDir().toFile(),
        "aartbars.json"
    );

    // Configuration fields with default values
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

    private ModConfig() {} // Prevent instantiation

    /**
     * Validates the configuration values.
     *
     * @throws IllegalArgumentException if any configuration value is invalid
     */
    public void validate() {
        validateOffset("stuckArrowsX", stuckArrowsX);
        validateOffset("stuckArrowsY", stuckArrowsY);
        validateOffset("speedometerX", speedometerX);
        validateOffset("speedometerY", speedometerY);
        validateOffset("thermometerX", thermometerX);
        validateOffset("thermometerY", thermometerY);
        validateOffset("brokenBlockTrackerX", brokenBlockTrackerX);
        validateOffset("brokenBlockTrackerY", brokenBlockTrackerY);
    }

    private void validateOffset(String fieldName, int value) {
        if (value < -1000 || value > 1000) {
            throw new IllegalArgumentException("Invalid offset value for " + fieldName + ": " + value);
        }
    }

    /**
     * Loads the configuration from file or creates a new one if it doesn't exist.
     *
     * @return the loaded or new configuration
     */
    @NotNull
    public static ModConfig load() {
        try {
            if (CONFIG_FILE.exists()) {
                try (FileReader reader = new FileReader(CONFIG_FILE)) {
                    return GSON.fromJson(reader, ModConfig.class);
                }
            }
        } catch (IOException e) {
            AartBars.LOGGER.error("Failed to load config", e);
        }
        return new ModConfig();
    }

    /**
     * Saves the current configuration to file.
     */
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            AartBars.LOGGER.error("Failed to save config", e);
        }
    }
}
