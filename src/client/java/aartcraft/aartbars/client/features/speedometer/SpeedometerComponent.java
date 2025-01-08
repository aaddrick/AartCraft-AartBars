package aartcraft.aartbars.client.features.speedometer;

import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Vec3d;
import java.util.Arrays;
import org.joml.Quaternionf;

public class SpeedometerComponent extends BaseHUDComponent {
    private final Vec3d[] positionHistory = new Vec3d[3];
    private int historyIndex = 0;
    private float currentSpeed = 0f;
    private float targetSpeed = 0f;
    private static final float LERP_FACTOR = 0.2f; // Adjust this for smoother/faster response
    
    public SpeedometerComponent() {
        super(0, 0);
        Arrays.fill(positionHistory, Vec3d.ZERO);
    }

    @Override
    public void render(DrawContext context, int screenWidth, int screenHeight) {
        this.x = screenWidth / 2 - 100;
        this.y = screenHeight - 50;
        
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return;

        // Calculate target speed
        targetSpeed = calculatePlayerSpeed(mc);
        
        // Lerp towards target speed
        currentSpeed = lerp(currentSpeed, targetSpeed, LERP_FACTOR);
        
        float rotation = calculateNeedleRotation(currentSpeed);
        drawSpeedometer(context, rotation, x, y, alpha);
    }

    private float lerp(float start, float end, float factor) {
        return start + factor * (end - start);
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof SpeedometerEvent speedEvent) {
            drawSpeedometer(speedEvent.context, 
                calculateNeedleRotation(currentSpeed), 
                speedEvent.x, speedEvent.y, 1f);
        }
    }

    private float calculatePlayerSpeed(MinecraftClient mc) {
        if (mc.player == null) return 0f;
        
        // Get current position
        Vec3d currentPos = mc.player.getPos();
        
        // Store current position in history
        positionHistory[historyIndex] = currentPos;
        historyIndex = (historyIndex + 1) % positionHistory.length;
        
        // Calculate average speed over last 3 ticks
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
        
        // Calculate average speed in blocks/tick
        float averageSpeed = totalDistance / validPositions;
        
        // Convert to blocks per second (20 ticks per second)
        return averageSpeed * 20f;
    }

    private float calculateNeedleRotation(float speed) {
        // Map speed to rotation angle (-90° to 90°)
        // Max speed is ~5.612 blocks/second when sprinting
        float maxSpeed = 6f; // Slightly above max sprinting speed
        return (speed / maxSpeed) * 180f - 90f;
    }

    private void drawSpeedometer(DrawContext context, float rotation, int x, int y, float alpha) {
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
        // Create quaternion for rotation around Z axis
        Quaternionf rotationQuat = new Quaternionf().rotateZ(rotation * (float)Math.PI / 180f);
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
