package aartcraft.aartbars.client.features.thermometer;

import aartcraft.aartbars.client.AartBarsClient;
import aartcraft.aartbars.client.components.BaseHUDComponent;
import aartcraft.aartbars.helpers.TextureHelper;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ThermometerComponent extends BaseHUDComponent {
    public ThermometerComponent() {
        super(0, 0);
    }

    @Override
    public void render(DrawContext context, int screenWidth, int screenHeight) {
        if (!AartBarsClient.config.showThermometer) return;
        this.x = screenWidth / 2 + 94;
        this.y = screenHeight - 58;
        
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null || mc.world == null) return;

        BlockPos playerpos = BlockPos.ofFloored(player.getPos());
        float temperature = mc.world.getBiome(playerpos).value().getTemperature();
        // AartBars.LOGGER.info(String.valueOf(temperature));
        
        drawThermometer(context, temperature, x, y, alpha);
    }

    @Override
    public void handleEvent(HUDOverlayEvent event) {
        if (event instanceof ThermometerEvent thermometerEvent) {
            drawThermometer(thermometerEvent.context, thermometerEvent.temperature, 
                          thermometerEvent.x, thermometerEvent.y, 1f);
        }
    }

    private void drawThermometer(DrawContext context, float temperature, int x, int y, float alpha) {
        enableAlpha(alpha);
        
        // Define min and max temperature range
        float minTemperature = -0.5f;
        float maxTemperature = 1.5f;
        int numSegments = 7; // Number of thermometer segments
        
        // Calculate the sprite index programmatically
        float segmentSize = (maxTemperature - minTemperature) / numSegments;
        int spriteIndex = (int) ((temperature - minTemperature) / segmentSize);
        spriteIndex = Math.min(numSegments - 1, Math.max(0, spriteIndex)); // Clamp to valid range (0-6)
        
        context.drawTexture(
            RenderLayer::getGuiTextured,
            TextureHelper.THERMOMETER_SPRITE,
            x, y,
            spriteIndex * 15f, 0f, // Select sprite segment (15px wide)
            15, 33, // Sprite size
            105, 33 // Texture dimensions (7 segments * 15px = 105px)
        );
        disableAlpha();
    }
}
