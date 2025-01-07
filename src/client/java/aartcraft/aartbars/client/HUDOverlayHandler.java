package aartcraft.aartbars.client;

import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsComponent;
import aartcraft.aartbars.client.features.stuckarrows.StuckArrowsEvent;
import aartcraft.aartbars.client.components.HUDComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class HUDOverlayHandler {
    public static HUDOverlayHandler INSTANCE;
    private final List<HUDComponent> components = new ArrayList<>();

    public static void init() {
        INSTANCE = new HUDOverlayHandler();

        // Register the stuck arrows component
        INSTANCE.registerComponent(new StuckArrowsComponent(
                MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 + 91,
                MinecraftClient.getInstance().getWindow().getScaledHeight() - 39
        ));

        // Register the stuck arrows event
        StuckArrowsEvent.EVENT.register(INSTANCE::onStuckArrowsRender);
    }

    public void registerComponent(HUDComponent component) {
        components.add(component);
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
}
