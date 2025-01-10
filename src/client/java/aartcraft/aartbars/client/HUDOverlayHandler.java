package aartcraft.aartbars.client;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.api.AartcraftApi;
import aartcraft.aartbars.client.components.stuckarrows.StuckArrowsComponent;
import aartcraft.aartbars.client.components.thermometer.ThermometerComponent;
import aartcraft.aartbars.client.components.brokenblocktracker.BrokenBlockTrackerComponent;
import aartcraft.aartbars.client.components.speedometer.SpeedometerComponent;
import aartcraft.aartbars.client.components.HUDComponent;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HUDOverlayHandler implements AartcraftApi {
    public static HUDOverlayHandler INSTANCE;
    private final List<HUDComponent> components = new ArrayList<>();

    public static void init() {
        INSTANCE = new HUDOverlayHandler();
        ModConfig config = AartBarsClient.config;

        // Register HUD rendering using Fabric's HudRenderCallback
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null || client.getWindow() == null) return;
            
            int screenWidth = client.getWindow().getScaledWidth();
            int screenHeight = client.getWindow().getScaledHeight();
            
            for (HUDComponent component : INSTANCE.components) {
                try {
                    component.render(drawContext, screenWidth, screenHeight);
                } catch (Exception e) {
                    AartBarsClient.LOGGER.error("Error rendering HUD component: {}", 
                        component.getClass().getSimpleName(), e);
                }
            }
        });

        // Register components based on config
        if (config.showStuckArrows) {
            INSTANCE.registerComponent(new StuckArrowsComponent(config));
        }
        if (config.showSpeedometer) {
            INSTANCE.registerComponent(new SpeedometerComponent(config));
        }
        if (config.showThermometer) {
            INSTANCE.registerComponent(new ThermometerComponent(config));
        }
        if (config.showBrokenBlockTracker) {
            INSTANCE.registerComponent(new BrokenBlockTrackerComponent(config));
        }
    }

    @Override
    public void registerEvents() {
        // No-op for now, but can be used to register additional events if needed
    }

    @Override
    public void registerComponent(HUDComponent component) {
        components.add(component);
    }
}
