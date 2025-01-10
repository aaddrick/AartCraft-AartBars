### **1. Replace `HUDOverlayEvent` with `HudRenderCallback`**
Your custom `HUDOverlayEvent` system can be replaced with Fabric's `HudRenderCallback` for rendering HUD components. This simplifies your code and aligns with Fabric's event system.

#### Changes:
1. **Remove `HUDOverlayEvent.java`**:
    - This file is no longer needed since Fabric's `HudRenderCallback` will handle rendering.

2. **Modify `HUDOverlayHandler.java`**:
    - Replace the custom event system with `HudRenderCallback`.

   ```java
   public class HUDOverlayHandler implements AartcraftApi {
       public static HUDOverlayHandler INSTANCE;
       private final List<HUDComponent> components = new ArrayList<>();

       public static void init() {
           INSTANCE = new HUDOverlayHandler();

           // Register HUD rendering using Fabric's HudRenderCallback
           HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
               int screenWidth = drawContext.getScaledWindowWidth();
               int screenHeight = drawContext.getScaledWindowHeight();

               for (HUDComponent component : INSTANCE.components) {
                   component.render(drawContext, screenWidth, screenHeight);
               }
           });

           // Register components (e.g., stuck arrows, speedometer, etc.)
           ModConfig config = AartBarsClient.config;
           if (config.showStuckArrows) {
               INSTANCE.registerComponent(new StuckArrowsComponent(config));
           }
           if (config.showSpeedometer) {
               INSTANCE.registerComponent(new SpeedometerComponent(config));
           }
           // Add other components as needed
       }

       @Override
       public void registerComponent(HUDComponent component) {
           components.add(component);
       }
   }
   ```

3. **Update `HUDComponent` implementations**:
    - Ensure all components (e.g., `StuckArrowsComponent`, `SpeedometerComponent`) implement the `render` method correctly.

---

### **2. Use `ScreenEvents` for Config Screens**
Your `ConfigScreen` and `OffsetConfigScreen` can be enhanced using Fabric's `ScreenEvents` for better integration with other mods and improved lifecycle management.

#### Changes:
1. **Modify `ConfigScreen.java`**:
    - Use `ScreenEvents.AFTER_INIT` to add custom buttons or modify the screen after initialization.

   ```java
   public final class ConfigScreen extends Screen {
       private final Screen parent;
       private final ModConfig config;

       public ConfigScreen(Screen parent, ModConfig config) {
           super(Text.of("AartBars Config"));
           this.parent = parent;
           this.config = config;
       }

       @Override
       protected void init() {
           // Add buttons and widgets here
       }

       @Override
       public void render(DrawContext context, int mouseX, int mouseY, float delta) {
           this.renderBackground(context);
           super.render(context, mouseX, mouseY, delta);
       }

       public static void register() {
           ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
               if (screen instanceof TitleScreen) {
                   // Add a button to open the config screen from the title screen
                   Screens.getButtons(screen).add(new ButtonWidget(
                       scaledWidth / 2 - 100, scaledHeight / 4 + 120,
                       200, 20,
                       Text.of("AartBars Config"),
                       button -> client.setScreen(new ConfigScreen(screen, AartBarsClient.config))
                   ));
               }
           });
       }
   }
   ```

2. **Modify `OffsetConfigScreen.java`**:
    - Use `ScreenEvents.BEFORE_RENDER` or `ScreenEvents.AFTER_RENDER` to handle custom rendering logic.

   ```java
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
                                 IntSupplier getX, IntSupplier getY, BiConsumer<Integer, Integer> setOffset) {
           super(Text.of("Offset Config"));
           this.parent = parent;
           this.config = config;
           this.featureName = featureName;
           this.getX = getX;
           this.getY = getY;
           this.setOffset = setOffset;
       }

       @Override
       protected void init() {
           // Initialize text fields and buttons
       }

       @Override
       public void render(DrawContext context, int mouseX, int mouseY, float delta) {
           this.renderBackground(context);
           super.render(context, mouseX, mouseY, delta);
       }

       public static void register() {
           ScreenEvents.BEFORE_RENDER.register((screen, context, mouseX, mouseY, delta) -> {
               if (screen instanceof OffsetConfigScreen) {
                   // Custom rendering logic for the offset config screen
               }
           });
       }
   }
   ```

---

### **3. Use `ClientTickEvents` for Component Updates**
If your HUD components need to update their state (e.g., speedometer calculations), use `ClientTickEvents`.

#### Changes:
1. **Modify `SpeedometerComponent.java`**:
    - Use `ClientTickEvents.END_CLIENT_TICK` to update the player's speed.

   ```java
   public final class SpeedometerComponent extends BaseHUDComponent {
       private float currentSpeed = 0f;

       public SpeedometerComponent(ModConfig config) {
           super(config);
           ClientTickEvents.END_CLIENT_TICK.register(client -> {
               if (client.player != null) {
                   currentSpeed = calculatePlayerSpeed(client);
               }
           });
       }

       @Override
       public void render(DrawContext context, int screenWidth, int screenHeight) {
           float rotation = calculateNeedleRotation(currentSpeed);
           drawSpeedometer(context, rotation, x, y, alpha);
       }

       private float calculatePlayerSpeed(MinecraftClient client) {
           // Existing logic to calculate player speed
       }

       private float calculateNeedleRotation(float speed) {
           // Existing logic to calculate needle rotation
       }

       private void drawSpeedometer(DrawContext context, float rotation, int x, int y, float alpha) {
           // Existing logic to draw the speedometer
       }
   }
   ```

---

### **4. Add Keybindings (Optional)**
If you want to add keybindings for toggling HUD components or opening config screens, use `KeyBindingHelper`.

#### Changes:
1. **Add Keybindings**:
    - Register keybindings in your mod's initialization.

   ```java
   public class AartBarsClient implements ClientModInitializer {
       public static ModConfig config;
       private static KeyBinding toggleHudKey;

       @Override
       public void onInitializeClient() {
           config = ModConfig.load();
           HUDOverlayHandler.init();

           // Register keybindings
           toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
               "key.aartbars.toggleHud",
               InputUtil.Type.KEYSYM,
               GLFW.GLFW_KEY_H,
               "category.aartbars"
           ));

           ClientTickEvents.END_CLIENT_TICK.register(client -> {
               while (toggleHudKey.wasPressed()) {
                   config.showHud = !config.showHud;
                   config.save();
               }
           });
       }
   }
   ```

---

### **Summary of Changes**
1. Replace `HUDOverlayEvent` with `HudRenderCallback`.
2. Use `ScreenEvents` for config screens.
3. Use `ClientTickEvents` for component updates.
4. Add keybindings (optional).