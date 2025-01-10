package aartcraft.aartbars.client;

import aartcraft.aartbars.api.AartcraftApi;
import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsComponent;
import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsEvent;
import aartcraft.aartbars.client.features.thermometer.ThermometerComponent;
import aartcraft.aartbars.client.features.thermometer.ThermometerEvent;
import aartcraft.aartbars.client.features.brokenblocktracker.BrokenBlockTrackerComponent;
import aartcraft.aartbars.client.features.brokenblocktracker.BrokenBlockTrackerEvent;
import aartcraft.aartbars.client.features.speedometer.SpeedometerComponent;
import aartcraft.aartbars.client.features.speedometer.SpeedometerEvent;
import aartcraft.aartbars.client.components.HUDComponent;
import aartcraft.aartbars.api.event.HUDOverlayEvent;
import aartcraft.aartbars.api.handler.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

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

    public void onRender(DrawContext context) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        for (HUDComponent component : components) {
            component.render(context, screenWidth, screenHeight);
        }
    }

    public void onStuckArrowsRender(StuckArrowsEvent event) {
        for (HUDComponent component : components) {
            component.handleEvent(event);
        }
    }

    public void onThermometerRender(ThermometerEvent event) {
        for (HUDComponent component : components) {
            component.handleEvent(event);
        }
    }

    public void onBrokenBlockTrackerRender(BrokenBlockTrackerEvent event) {
        for (HUDComponent component : components) {
            component.handleEvent(event);
        }
    }

    public void onSpeedometerRender(SpeedometerEvent event) {
        for (HUDComponent component : components) {
            component.handleEvent(event);
        }
    }
}
