# AartBars - HUD Components

A lightweight Minecraft mod that adds various HUD components including stuck arrows and speedometer.

## Features

### Stuck Arrows
- Displays the number of arrows stuck in your player
- Clean, minimalistic arrow icons
- Subtle shake animation when arrows are added
- Customizable position (right-aligned with food bar by default)

### Speedometer
- Real-time speed measurement
- Smooth analog needle movement
- Positioned left of center by default
- Accurate speed calculation using position history
- Smooth interpolation for needle movement

## Technical Details

### Stuck Arrows
- Renders up to 10 arrows per row, with multiple rows if needed
- Uses Minecraft's native rendering system for optimal performance
- Includes alpha blending for smooth transitions
- Shake effect lasts for 1 second (20 ticks) after arrow count changes
- Icons are 8x8 pixels with 2px spacing

### Speedometer
- Measures speed in blocks/tick (1 block/tick = 20 blocks/second)
- Uses position history for accurate speed calculation
- Smooth interpolation for needle movement
- 32x32 pixel gauge with rotating needle
- Max speed detection of 0.3 blocks/tick (6 blocks/second)

## Event API

The mod provides an event-based API for other mods to interact with:

```java
// Stuck Arrows Event
HUDOverlayEvent.StuckArrows.EVENT.register(event -> {
    // Custom rendering logic
    event.isCanceled = true; // To prevent default rendering
});

// Speedometer Event
HUDOverlayEvent.Speedometer.EVENT.register(event -> {
    // Custom rendering logic
    event.isCanceled = true; // To prevent default rendering
});
```

Key event properties:
- `stuckarrows`: Current number of stuck arrows (Stuck Arrows Event)
- `speed`: Current speed in blocks/tick (Speedometer Event)
- `x`, `y`: Default render position
- `context`: DrawContext for custom rendering
- `isCanceled`: Set to true to prevent default rendering

## Installation

1. Download the mod from CurseForge
2. Place the mod file in your `mods` folder
3. Launch Minecraft with Fabric Loader installed

## Compatibility

- Works with Fabric API
- Compatible with most HUD mods
- Client-side only - no server installation needed

## Configuration

The mod works out-of-the-box with default settings. No configuration required!

## Credits

- **Developer**: AartCraft Team
- **Contributors**: [Your Name Here]
- **Special Thanks**: Minecraft Modding Community

## License

This mod is released under the [Unlicense](LICENSE.txt), meaning you're free to use, modify, and distribute it however you like!

```

## Screenshots

![Stuck Arrows HUD Example](screenshots/example.png) 
*Note: Add your screenshot to the screenshots folder*
