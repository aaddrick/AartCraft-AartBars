package aartcraft.aartbars.client.features.thermometer;

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
        this.x = screenWidth / 2;
        this.y = screenHeight / 2;
        
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null || mc.world == null) return;

        BlockPos playerpos = BlockPos.ofFloored(player.getPos());
        float temperature = mc.world.getBiome(playerpos).value().getTemperature();
        
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
        
        // Map temperature from -1.0 to 1.0 to sprite index (0-6)
        int spriteIndex;
        if (temperature < -0.857f) { // -1.0 to -0.857
            spriteIndex = 0; // Coldest
        } else if (temperature < -0.571f) { // -0.857 to -0.571
            spriteIndex = 1;
        } else if (temperature < -0.285f) { // -0.571 to -0.285
            spriteIndex = 2;
        } else if (temperature < 0.0f) { // -0.285 to 0.0
            spriteIndex = 3;
        } else if (temperature < 0.285f) { // 0.0 to 0.285
            spriteIndex = 4;
        } else if (temperature < 0.571f) { // 0.285 to 0.571
            spriteIndex = 5;
        } else { // 0.571 to 1.0
            spriteIndex = 6; // Hottest
        }
        
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
