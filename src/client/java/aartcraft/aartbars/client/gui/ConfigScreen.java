package aartcraft.aartbars.client.gui;

import aartcraft.aartbars.AartBars;
import aartcraft.aartbars.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final ModConfig config;

    protected ConfigScreen(Screen parent) {
        super(Text.translatable("text.aartbars.config.title"));
        this.parent = parent != null ? parent : this.client.currentScreen;
        this.config = AartBars.config;
    }

    @Override
    protected void init() {
        // Stuck Arrows Toggle
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.stuck_arrows", 
                config.showStuckArrows ? "ON" : "OFF"),
            button -> {
                config.showStuckArrows = !config.showStuckArrows;
                button.setMessage(Text.translatable("text.aartbars.config.stuck_arrows", 
                    config.showStuckArrows ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 24, 200, 20)
            .build());

        // Speedometer Toggle
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.speedometer", 
                config.showSpeedometer ? "ON" : "OFF"),
            button -> {
                config.showSpeedometer = !config.showSpeedometer;
                button.setMessage(Text.translatable("text.aartbars.config.speedometer", 
                    config.showSpeedometer ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 48, 200, 20)
            .build());

        // Thermometer Toggle
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.thermometer", 
                config.showThermometer ? "ON" : "OFF"),
            button -> {
                config.showThermometer = !config.showThermometer;
                button.setMessage(Text.translatable("text.aartbars.config.thermometer", 
                    config.showThermometer ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 72, 200, 20)
            .build());

        // Done Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.done"),
            button -> {
                config.save();
                this.client.setScreen(parent);
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
            .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null) return;
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
