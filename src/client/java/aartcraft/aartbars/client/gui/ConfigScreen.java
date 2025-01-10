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
        TextFieldWidget stuckArrowsXField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + spacing, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.x_offset", config.stuckArrowsX)
        );
        stuckArrowsXField.setText(String.valueOf(config.stuckArrowsX));
        stuckArrowsXField.setChangedListener(value -> {
            try {
                config.stuckArrowsX = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.stuckArrowsX = 0;
            }
        });
        this.addDrawableChild(stuckArrowsXField);
        
        // Y Offset Field
        TextFieldWidget stuckArrowsYField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.y_offset", config.stuckArrowsY)
        );
        stuckArrowsYField.setText(String.valueOf(config.stuckArrowsY));
        stuckArrowsYField.setChangedListener(value -> {
            try {
                config.stuckArrowsY = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.stuckArrowsY = 0;
            }
        });
        this.addDrawableChild(stuckArrowsYField);

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
        
        // X Offset Field
        TextFieldWidget speedometerXField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + spacing, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.x_offset", config.speedometerX)
        );
        speedometerXField.setText(String.valueOf(config.speedometerX));
        speedometerXField.setChangedListener(value -> {
            try {
                config.speedometerX = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.speedometerX = 0;
            }
        });
        this.addDrawableChild(speedometerXField);
        
        // Y Offset Field
        TextFieldWidget speedometerYField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.y_offset", config.speedometerY)
        );
        speedometerYField.setText(String.valueOf(config.speedometerY));
        speedometerYField.setChangedListener(value -> {
            try {
                config.speedometerY = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.speedometerY = 0;
            }
        });
        this.addDrawableChild(speedometerYField);

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
        
        // X Offset Field
        TextFieldWidget thermometerXField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + spacing, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.x_offset", config.thermometerX)
        );
        thermometerXField.setText(String.valueOf(config.thermometerX));
        thermometerXField.setChangedListener(value -> {
            try {
                config.thermometerX = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.thermometerX = 0;
            }
        });
        this.addDrawableChild(thermometerXField);
        
        // Y Offset Field
        TextFieldWidget thermometerYField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.y_offset", config.thermometerY)
        );
        thermometerYField.setText(String.valueOf(config.thermometerY));
        thermometerYField.setChangedListener(value -> {
            try {
                config.thermometerY = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.thermometerY = 0;
            }
        });
        this.addDrawableChild(thermometerYField);

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
        
        // X Offset Field
        TextFieldWidget brokenBlockTrackerXField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + spacing, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.x_offset", config.brokenBlockTrackerX)
        );
        brokenBlockTrackerXField.setText(String.valueOf(config.brokenBlockTrackerX));
        brokenBlockTrackerXField.setChangedListener(value -> {
            try {
                config.brokenBlockTrackerX = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.brokenBlockTrackerX = 0;
            }
        });
        this.addDrawableChild(brokenBlockTrackerXField);
        
        // Y Offset Field
        TextFieldWidget brokenBlockTrackerYField = new TextFieldWidget(
            this.textRenderer,
            this.width / 2 + offsetFieldWidth + spacing * 2, buttonY, offsetFieldWidth, 20,
            Text.translatable("text.aartbars.config.y_offset", config.brokenBlockTrackerY)
        );
        brokenBlockTrackerYField.setText(String.valueOf(config.brokenBlockTrackerY));
        brokenBlockTrackerYField.setChangedListener(value -> {
            try {
                config.brokenBlockTrackerY = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                config.brokenBlockTrackerY = 0;
            }
        });
        this.addDrawableChild(brokenBlockTrackerYField);

        // Done Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.done"),
            button -> {
                config.save();
                if (this.client != null) {
                    this.client.setScreen(parent);
                }
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
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
