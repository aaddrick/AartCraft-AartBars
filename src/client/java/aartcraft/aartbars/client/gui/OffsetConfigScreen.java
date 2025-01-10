package aartcraft.aartbars.client.gui;

import aartcraft.aartbars.ModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.function.BiConsumer;
import java.util.function.IntSupplier;

public class OffsetConfigScreen extends Screen {
    private final Screen parent;
    private final ModConfig config;
    private final String featureName;
    private final IntSupplier getX;
    private final IntSupplier getY;
    private final BiConsumer<Integer, Integer> setOffset;
    
    private TextFieldWidget xField;
    private TextFieldWidget yField;

    public OffsetConfigScreen(Screen parent, ModConfig config, String featureName,
                            IntSupplier getX, IntSupplier getY,
                            BiConsumer<Integer, Integer> setOffset) {
        super(Text.translatable("text.aartbars.config.offset_title", featureName));
        this.parent = parent;
        this.config = config;
        this.featureName = featureName;
        this.getX = getX;
        this.getY = getY;
        this.setOffset = setOffset;
    }

    @Override
    protected void init() {
        // X Offset Field
        xField = new TextFieldWidget(this.textRenderer, 
            this.width / 2 - 100, this.height / 2 - 24, 
            200, 20, Text.of("X Offset"));
        xField.setText(String.valueOf(getX.getAsInt()));
        this.addSelectableChild(xField);
        
        // Y Offset Field
        yField = new TextFieldWidget(this.textRenderer,
            this.width / 2 - 100, this.height / 2 + 4,
            200, 20, Text.of("Y Offset"));
        yField.setText(String.valueOf(getY.getAsInt()));
        this.addSelectableChild(yField);
        
        // Save Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("text.aartbars.config.save"),
            button -> {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    setOffset.accept(x, y);
                    config.save();
                    this.client.setScreen(parent);
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            })
            .dimensions(this.width / 2 - 100, this.height / 2 + 34, 200, 20)
            .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
