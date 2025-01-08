package aartcraft.aartbars.client.features.speedometer;

import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
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
        
        drawSpeedometer(context, speed, rotation, x, y, alpha);
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof SpeedometerEvent speedEvent) {
            drawSpeedometer(speedEvent.context, speedEvent.speed, 
                          speedEvent.rotation, speedEvent.x, speedEvent.y, 1f);
        }
    }

    private float calculatePlayerSpeed(MinecraftClient mc) {
        Vec3d currentPos = mc.player.getPos();
        long currentTime = System.currentTimeMillis();
        
        if (lastUpdateTime == 0) {
            lastUpdateTime = currentTime;
            return 0;
        }
        
        float timeDelta = (currentTime - lastUpdateTime) / 1000f;
        if (timeDelta < 0.1f) return lastSpeed;
        
        float distance = (float) mc.player.getPos().distanceTo(currentPos);
        float speed = distance / timeDelta;
        
        lastSpeed = speed;
        lastUpdateTime = currentTime;
        
        return speed;
    }

    private float calculateNeedleRotation(float speed) {
        // Map speed to rotation angle (-90° to 90°)
        float maxSpeed = 10f; // 10 blocks per second
        return Math.min(90, Math.max(-90, (speed / maxSpeed) * 90));
    }

    private void drawSpeedometer(DrawContext context, float speed, float rotation, int x, int y, float alpha) {
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
