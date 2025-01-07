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
        this.x = screenWidth / 2 - 91;
        this.y = screenHeight - 39;
        
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
        
        // Map temperature to sprite index (0-7)
        int spriteIndex = Math.min(7, Math.max(0, (int)(temperature * 7)));
        
        context.drawTexture(
            RenderLayer::getGuiTextured,
            TextureHelper.THERMOMETER_SPRITE,
            x, y,
            spriteIndex * 15f, 0f, // Select sprite segment
            15, 15, // Sprite size
            120, 15 // Texture dimensions
        );
        disableAlpha();
    }
}
