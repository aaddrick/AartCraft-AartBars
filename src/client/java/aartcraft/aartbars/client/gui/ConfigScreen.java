package aartcraft.aartbars.client.gui;

import aartcraft.aartbars.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final ModConfig config;

    public ConfigScreen(Screen parent, ModConfig config) {
        super(Text.translatable("text.aartbars.config.title"));
        // Ensure we have a valid client reference
        this.client = MinecraftClient.getInstance();
        this.parent = parent != null ? parent : this.client.currentScreen;
        this.config = config;
    }

    @Override
    protected void init() {
        int buttonY = this.height / 4 + 24;
        int buttonWidth = 100;
        int offsetFieldWidth = 50;
        int spacing = 4;
        
        // Stuck Arrows Toggle and Offset Fields
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.stuck_arrows", 
                config.showStuckArrows ? "ON" : "OFF"),
            button -> {
                config.showStuckArrows = !config.showStuckArrows;
                button.setMessage(Text.translatable("text.aartbars.config.stuck_arrows", 
                    config.showStuckArrows ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - buttonWidth - spacing, buttonY, buttonWidth, 20)
            .build());
        
        // X Offset Field
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.x_offset", config.stuckArrowsX),
            this.textRenderer)
            .setText(String.valueOf(config.stuckArrowsX))
            .setChangedListener(value -> {
                try {
                    config.stuckArrowsX = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + spacing, buttonY, offsetFieldWidth, 20)
            .build());
        
        // Y Offset Field
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.y_offset", config.stuckArrowsY),
            this.textRenderer)
            .setText(String.valueOf(config.stuckArrowsY))
            .setChangedListener(value -> {
                try {
                    config.stuckArrowsY = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20)
            .build());

        buttonY += 24;
        
        // Speedometer Toggle and Offset Fields
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.speedometer", 
                config.showSpeedometer ? "ON" : "OFF"),
            button -> {
                config.showSpeedometer = !config.showSpeedometer;
                button.setMessage(Text.translatable("text.aartbars.config.speedometer", 
                    config.showSpeedometer ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - buttonWidth - spacing, buttonY, buttonWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.x_offset", config.speedometerX),
            this.textRenderer)
            .setText(String.valueOf(config.speedometerX))
            .setChangedListener(value -> {
                try {
                    config.speedometerX = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + spacing, buttonY, offsetFieldWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.y_offset", config.speedometerY),
            this.textRenderer)
            .setText(String.valueOf(config.speedometerY))
            .setChangedListener(value -> {
                try {
                    config.speedometerY = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20)
            .build());

        buttonY += 24;
        
        // Thermometer Toggle and Offset Fields
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.thermometer", 
                config.showThermometer ? "ON" : "OFF"),
            button -> {
                config.showThermometer = !config.showThermometer;
                button.setMessage(Text.translatable("text.aartbars.config.thermometer", 
                    config.showThermometer ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - buttonWidth - spacing, buttonY, buttonWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.x_offset", config.thermometerX),
            this.textRenderer)
            .setText(String.valueOf(config.thermometerX))
            .setChangedListener(value -> {
                try {
                    config.thermometerX = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + spacing, buttonY, offsetFieldWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.y_offset", config.thermometerY),
            this.textRenderer)
            .setText(String.valueOf(config.thermometerY))
            .setChangedListener(value -> {
                try {
                    config.thermometerY = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20)
            .build());

        buttonY += 24;
        
        // Broken Block Tracker Toggle and Offset Fields
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.broken_block_tracker", 
                config.showBrokenBlockTracker ? "ON" : "OFF"),
            button -> {
                config.showBrokenBlockTracker = !config.showBrokenBlockTracker;
                button.setMessage(Text.translatable("text.aartbars.config.broken_block_tracker", 
                    config.showBrokenBlockTracker ? "ON" : "OFF"));
            })
            .dimensions(this.width / 2 - buttonWidth - spacing, buttonY, buttonWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.x_offset", config.brokenBlockTrackerX),
            this.textRenderer)
            .setText(String.valueOf(config.brokenBlockTrackerX))
            .setChangedListener(value -> {
                try {
                    config.brokenBlockTrackerX = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + spacing, buttonY, offsetFieldWidth, 20)
            .build());
        
        this.addDrawableChild(TextFieldWidget.builder(
            Text.translatable("text.aartbars.config.y_offset", config.brokenBlockTrackerY),
            this.textRenderer)
            .setText(String.valueOf(config.brokenBlockTrackerY))
            .setChangedListener(value -> {
                try {
                    config.brokenBlockTrackerY = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20)
            .build());

        // Stuck Arrows Offset Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.stuck_arrows_offset", 
                config.stuckArrowsX, config.stuckArrowsY),
            button -> {
                this.client.setScreen(new OffsetConfigScreen(
                    this, config, 
                    "Stuck Arrows", 
                    () -> config.stuckArrowsX, 
                    () -> config.stuckArrowsY,
                    (x, y) -> {
                        config.stuckArrowsX = x;
                        config.stuckArrowsY = y;
                    }
                ));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
            .build());

        // Speedometer Offset Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.speedometer_offset", 
                config.speedometerX, config.speedometerY),
            button -> {
                this.client.setScreen(new OffsetConfigScreen(
                    this, config, 
                    "Speedometer", 
                    () -> config.speedometerX, 
                    () -> config.speedometerY,
                    (x, y) -> {
                        config.speedometerX = x;
                        config.speedometerY = y;
                    }
                ));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 144, 200, 20)
            .build());

        // Thermometer Offset Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.thermometer_offset", 
                config.thermometerX, config.thermometerY),
            button -> {
                this.client.setScreen(new OffsetConfigScreen(
                    this, config, 
                    "Thermometer", 
                    () -> config.thermometerX, 
                    () -> config.thermometerY,
                    (x, y) -> {
                        config.thermometerX = x;
                        config.thermometerY = y;
                    }
                ));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 168, 200, 20)
            .build());

        // Broken Block Tracker Offset Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.broken_block_tracker_offset", 
                config.brokenBlockTrackerX, config.brokenBlockTrackerY),
            button -> {
                this.client.setScreen(new OffsetConfigScreen(
                    this, config, 
                    "Broken Block Tracker", 
                    () -> config.brokenBlockTrackerX, 
                    () -> config.brokenBlockTrackerY,
                    (x, y) -> {
                        config.brokenBlockTrackerX = x;
                        config.brokenBlockTrackerY = y;
                    }
                ));
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 192, 200, 20)
            .build());

        // Done Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.done"),
            button -> {
                config.save();
                if (this.client != null) {
                    this.client.setScreen(parent);
                }
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 216, 200, 20)
            .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.textRenderer == null) return;
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
