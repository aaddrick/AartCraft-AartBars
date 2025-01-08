package aartcraft.aartbars.client.features.speedometer;

import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

public class SpeedometerComponent extends BaseHUDComponent {
    private float lastSpeed = 0;
    private long lastUpdateTime = 0;
    
    public SpeedometerComponent() {
        super(0, 0);
    }

    @Override
    public void render(DrawContext context, int screenWidth, int screenHeight) {
        this.x = screenWidth / 2 - 100; // Left side position
        this.y = screenHeight - 50; // Bottom position
        
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return;

        // Calculate speed
        float speed = calculatePlayerSpeed(mc);
        float rotation = calculateNeedleRotation(speed);
        
        drawSpeedometer(context, rotation, x, y, alpha);
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof SpeedometerEvent speedEvent) {
            drawSpeedometer(speedEvent.context, speedEvent.rotation, 
                          speedEvent.x, speedEvent.y, 1f);
        }
    }

    private float calculatePlayerSpeed(MinecraftClient mc) {
        if (mc.player == null) return 0f;
        
        // Get base movement speed from attributes
        float baseSpeed = mc.player.getMovementSpeed();
        
        // Get actual movement speed based on player state
        float actualSpeed = baseSpeed;
        
        // Check if player is sprinting
        if (mc.player.isSprinting()) {
            actualSpeed *= 1.3f; // Sprinting multiplier
        }
        
        // Check if player is sneaking
        if (mc.player.isSneaking()) {
            actualSpeed *= 0.3f; // Sneaking multiplier
        }
        
        // Convert to blocks per second (1 block = 1 meter)
        // Base walking speed is ~4.317 m/s (4.317 blocks per second)
        // Sprinting speed is ~5.612 m/s
        return actualSpeed * 20f; // Multiply by 20 to convert from blocks/tick to blocks/second
    }

    private float calculateNeedleRotation(float speed) {
        // Map speed to rotation angle (-90° to 90°)
        // Max speed is ~5.612 blocks/second when sprinting
        float maxSpeed = 6f; // Slightly above max sprinting speed
        return Math.min(90, Math.max(-90, (speed / maxSpeed) * 90));
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
