package aartcraft.aartbars.client.components.speedometer;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Objects;
import org.joml.Quaternionf;

/**
 * HUD component that displays the player's current speed.
 */

@ApiStatus.Experimental
public final class SpeedometerComponent extends BaseHUDComponent {
    private static final int HISTORY_SIZE = 10;
    private static final float LERP_FACTOR = 0.2f; // Adjust this for smoother/faster response
    private static final float MAX_SPEED = 40f; // Slightly above max sprinting speed

    private final Vec3d[] positionHistory = new Vec3d[HISTORY_SIZE];
    private int historyIndex = 0;
    private float currentSpeed = 0f;

    /**
     * Creates a new SpeedometerComponent.
     *
     * @param config the mod configuration, must not be null
     * @throws NullPointerException if config is null
     */
    public SpeedometerComponent(@NotNull ModConfig config) {
        super(0, 0, config);
        Arrays.fill(positionHistory, Vec3d.ZERO);
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Calculate target speed
                float targetSpeed = calculatePlayerSpeed(client);
                // Lerp towards target speed
                currentSpeed = lerp(currentSpeed, targetSpeed);
            }
        });
    }

    @Override
    public void render(@NotNull DrawContext context, int screenWidth, int screenHeight) {
        Objects.requireNonNull(context, "DrawContext cannot be null");

        if (!AartBarsClient.config.showSpeedometer) return;

        // Update position using config offsets
        this.x = screenWidth / 2 + 115 + config.speedometerX;
        this.y = screenHeight - 58 + config.speedometerY;

        // Calculate needle rotation and render the speedometer
        float rotation = calculateNeedleRotation(currentSpeed);
        drawSpeedometer(context, rotation, x, y, alpha);
    }

    /**
     * Calculates the player's speed based on their position history.
     *
     * @param mc the Minecraft client instance
     * @return the player's speed in blocks per second
     */
    private float calculatePlayerSpeed(@NotNull MinecraftClient mc) {
        Objects.requireNonNull(mc, "MinecraftClient cannot be null");

        if (mc.player == null) return 0f;

        // Get current position
        Vec3d currentPos = mc.player.getPos();

        // Store current position in history
        positionHistory[historyIndex] = currentPos;
        historyIndex = (historyIndex + 1) % HISTORY_SIZE;

        // Calculate average speed over the history
        float totalDistance = 0f;
        int validPositions = 0;

        for (Vec3d prevPos : positionHistory) {
            if (prevPos != null) {
                totalDistance += (float) currentPos.distanceTo(prevPos);
                validPositions++;
            }
        }

        // If we don't have enough history yet, return 0
        if (validPositions < 2) return 0f;

        // Calculate average speed in blocks per second
        return (totalDistance / validPositions) * 20f;
    }

    /**
     * Linearly interpolates between two values.
     *
     * @param start the starting value
     * @param end the target value
     * @return the interpolated value
     */
    private float lerp(float start, float end) {
        return start + LERP_FACTOR * (end - start);
    }

    /**
     * Calculates the needle rotation based on the player's speed.
     *
     * @param speed the player's speed in blocks per second
     * @return the needle rotation in degrees
     */
    private float calculateNeedleRotation(float speed) {
        // Clamp the speed to prevent over-rotation
        speed = Math.min(speed, MAX_SPEED);
        return (speed / MAX_SPEED) * 270f;
    }

    /**
     * Draws the speedometer on the screen.
     *
     * @param context the draw context, must not be null
     * @param rotation the needle rotation in degrees
     * @param x the x position
     * @param y the y position
     * @param alpha the alpha value for transparency
     */
    private void drawSpeedometer(@NotNull DrawContext context, float rotation, int x, int y, float alpha) {
        Objects.requireNonNull(context, "DrawContext cannot be null");

        enableAlpha(alpha);

        // Draw base
        context.drawTexture(
            RenderLayer::getGuiTextured,
            TextureHelper.SPEEDOMETER_BASE,
            x, y,
            0, 0,
            32, 32,
            32, 32
        );

        // Draw needle with rotation
        context.getMatrices().push();
        context.getMatrices().translate(x + 16, y + 16, 0);
        Quaternionf rotationQuat = new Quaternionf().rotateZ(rotation * (float) Math.PI / 180f);
        context.getMatrices().multiply(rotationQuat);
        context.drawTexture(
            RenderLayer::getGuiTextured,
            TextureHelper.SPEEDOMETER_NEEDLE,
            -16, -16,
            0, 0,
            32, 32,
            32, 32
        );
        context.getMatrices().pop();

        disableAlpha();
    }
}
