package aartcraft.aartbars.client;

import aartcraft.aartbars.ModConfig;
import aartcraft.aartbars.api.AartcraftApi;
import aartcraft.aartbars.client.components.stuckarrows.StuckArrowsComponent;
import aartcraft.aartbars.client.components.stuckarrows.StuckArrowsEvent;
import aartcraft.aartbars.client.components.thermometer.ThermometerComponent;
import aartcraft.aartbars.client.components.thermometer.ThermometerEvent;
import aartcraft.aartbars.client.components.brokenblocktracker.BrokenBlockTrackerComponent;
import aartcraft.aartbars.client.components.brokenblocktracker.BrokenBlockTrackerEvent;
import aartcraft.aartbars.client.components.speedometer.SpeedometerComponent;
import aartcraft.aartbars.client.components.speedometer.SpeedometerEvent;
import aartcraft.aartbars.client.components.HUDComponent;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
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

        // Register the stuck arrows component
        INSTANCE.registerComponent(new StuckArrowsComponent(config));
        // Register the thermometer component
        INSTANCE.registerComponent(new ThermometerComponent(config));
        // Register the broken block tracker component
        INSTANCE.registerComponent(new BrokenBlockTrackerComponent(config));
        // Register the speedometer component
        INSTANCE.registerComponent(new SpeedometerComponent(config));

        // Register events
        StuckArrowsEvent.EVENT.register(INSTANCE::onStuckArrowsRender);
        ThermometerEvent.EVENT.register(INSTANCE::onThermometerRender);
        BrokenBlockTrackerEvent.EVENT.register(INSTANCE::onBrokenBlockTrackerRender);
        // Register speedometer event
        SpeedometerEvent.EVENT.register(INSTANCE::onSpeedometerRender);
    }

    @Override
    public void registerEvents() {
        // Default implementation does nothing
    }

    @Override
    public void registerComponent(HUDComponent component) {
        components.add(component);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends HUDOverlayEvent> void registerEvent(Class<T> eventClass, EventHandler<T> handler) {
        if (eventClass == StuckArrowsEvent.class) {
            StuckArrowsEvent.EVENT.register((EventHandler<StuckArrowsEvent>) handler);
        } else if (eventClass == ThermometerEvent.class) {
            ThermometerEvent.EVENT.register((EventHandler<ThermometerEvent>) handler);
        } else if (eventClass == BrokenBlockTrackerEvent.class) {
            BrokenBlockTrackerEvent.EVENT.register((EventHandler<BrokenBlockTrackerEvent>) handler);
        } else if (eventClass == SpeedometerEvent.class) {
            SpeedometerEvent.EVENT.register((EventHandler<SpeedometerEvent>) handler);
        }
    }

    public void onRender(@NotNull DrawContext context) {
        Objects.requireNonNull(context, "DrawContext cannot be null");
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.getWindow() == null) {
            return;
        }

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        for (HUDComponent component : components) {
            try {
                component.render(context, screenWidth, screenHeight);
            } catch (Exception e) {
                AartBarsClient.LOGGER.error("Error rendering HUD component: {}", component.getClass().getSimpleName(), e);
            }
        }
    }

    public void onStuckArrowsRender(@NotNull StuckArrowsEvent event) {
        Objects.requireNonNull(event, "StuckArrowsEvent cannot be null");
        
        for (HUDComponent component : components) {
            try {
                component.handleEvent(event);
            } catch (Exception e) {
                AartBarsClient.LOGGER.error("Error handling StuckArrowsEvent in component: {}", 
                    component.getClass().getSimpleName(), e);
            }
        }
    }

    public void onThermometerRender(@NotNull ThermometerEvent event) {
        Objects.requireNonNull(event, "ThermometerEvent cannot be null");
        
        for (HUDComponent component : components) {
            try {
                component.handleEvent(event);
            } catch (Exception e) {
                AartBarsClient.LOGGER.error("Error handling ThermometerEvent in component: {}", 
                    component.getClass().getSimpleName(), e);
            }
        }
    }

    public void onBrokenBlockTrackerRender(@NotNull BrokenBlockTrackerEvent event) {
        Objects.requireNonNull(event, "BrokenBlockTrackerEvent cannot be null");
        
        for (HUDComponent component : components) {
            try {
                component.handleEvent(event);
            } catch (Exception e) {
                AartBarsClient.LOGGER.error("Error handling BrokenBlockTrackerEvent in component: {}", 
                    component.getClass().getSimpleName(), e);
            }
        }
    }

    public void onSpeedometerRender(@NotNull SpeedometerEvent event) {
        Objects.requireNonNull(event, "SpeedometerEvent cannot be null");
        
        for (HUDComponent component : components) {
            try {
                component.handleEvent(event);
            } catch (Exception e) {
                AartBarsClient.LOGGER.error("Error handling SpeedometerEvent in component: {}", 
                    component.getClass().getSimpleName(), e);
            }
        }
    }
}
