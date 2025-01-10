package aartcraft.aartbars.client.features.thermometer;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

/**
 * HUD component that displays the player's current temperature.
 */
@ApiStatus.Experimental
public final class ThermometerComponent extends BaseHUDComponent {
    /**
     * HUD component that displays the player's current temperature.
     * <p>
     * This component reads the biome temperature at the player's location
     * and displays it using a thermometer graphic.
     * <p>
     * <b>Experimental feature</b>, may be removed or changed without further notice.
     */
    private static final int NUM_SEGMENTS = 7; // Number of thermometer segments
    private static final float MIN_TEMPERATURE = -0.5f; // Minimum temperature value
    private static final float MAX_TEMPERATURE = 1.5f; // Maximum temperature value
    private static final int SEGMENT_WIDTH = 15; // Width of each thermometer segment
    private static final int THERMOMETER_HEIGHT = 33; // Height of the thermometer sprite

    /**
     * Creates a new ThermometerComponent.
     *
     * @param config the mod configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public ThermometerComponent(@NotNull ModConfig config) {
        super(0, 0, config);
    }

    @Override
    public void render(@NotNull DrawContext context, int screenWidth, int screenHeight) {
        Objects.requireNonNull(context, "DrawContext cannot be null");

        if (!AartBarsClient.config.showThermometer) return;

        // Update position using config offsets
        this.x = screenWidth / 2 + 94 + config.thermometerX;
        this.y = screenHeight - 58 + config.thermometerY;

        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null || mc.world == null) return;

        BlockPos playerPos = BlockPos.ofFloored(player.getPos());
        float temperature = mc.world.getBiome(playerPos).value().getTemperature();

        drawThermometer(context, temperature, x, y, alpha);
    }

    @Override
    public void handleEvent(@NotNull HUDOverlayEvent event) {
        Objects.requireNonNull(event, "HUDOverlayEvent cannot be null");

        if (event instanceof ThermometerEvent thermometerEvent) {
            drawThermometer(thermometerEvent.context, thermometerEvent.temperature,
                thermometerEvent.x, thermometerEvent.y, 1f);
        }
    }

    /**
     * Draws the thermometer on the screen.
     *
     * @param context the draw context, must not be null
     * @param temperature the current temperature value
     * @param x the x position
     * @param y the y position
     * @param alpha the alpha value for transparency
     */
    private void drawThermometer(@NotNull DrawContext context, float temperature, int x, int y, float alpha) {
        Objects.requireNonNull(context, "DrawContext cannot be null");

        enableAlpha(alpha);

        // Calculate the sprite index based on temperature
        float segmentSize = (MAX_TEMPERATURE - MIN_TEMPERATURE) / NUM_SEGMENTS;
        int spriteIndex = (int) ((temperature - MIN_TEMPERATURE) / segmentSize);
        spriteIndex = Math.min(NUM_SEGMENTS - 1, Math.max(0, spriteIndex)); // Clamp to valid range

        // Draw the thermometer segment
        context.drawTexture(
            RenderLayer::getGuiTextured,
            TextureHelper.THERMOMETER_SPRITE,
            x, y,
            spriteIndex * SEGMENT_WIDTH, 0f, // Select sprite segment
            SEGMENT_WIDTH, THERMOMETER_HEIGHT, // Sprite size
            NUM_SEGMENTS * SEGMENT_WIDTH, THERMOMETER_HEIGHT // Texture dimensions
        );

        disableAlpha();
    }
}
