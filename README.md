# AartBars - HUD Components

A lightweight Minecraft mod that adds various HUD components.

## Features

### Broken Block Tracker
- Tracks and displays the number of blocks broken by the player
- Simple text-based display
- Customizable position (right-aligned with experience bar by default)
- Counter persists across game sessions

### Stuck Arrows
- Displays the number of arrows stuck in your player
- Arrow icon rendering
- Customizable position (right-aligned with food bar by default)
- Basic animation when arrow count changes

### Speedometer
- Measures player movement speed
- Analog gauge display
- Customizable position (left of center by default)
- Smooth interpolation between speed changes
- Uses position history for accurate speed calculation

### Thermometer
- Displays current biome temperature
- Graphical thermometer display
- Customizable position (left-aligned with health bar by default)
- Temperature range from -0.5 to 1.5
- Updates based on player's current biome

## Technical Details

### Broken Block Tracker
- Uses static counter for block tracking
- Basic text rendering with shadow
- Configurable position offsets
- Validates position values

### Stuck Arrows
- Renders up to 10 arrows per row
- Uses 8x8 pixel arrow icons
- 2px spacing between arrows
- Basic shake animation on arrow count change
- 20 tick animation duration

### Speedometer
- Uses 10-point position history
- Linear interpolation for smooth transitions
- 32x32 pixel gauge display
- Rotating needle implementation
- Max speed detection of 40 blocks/second

### Thermometer
- Uses Minecraft's biome temperature system
- 7-segment thermometer display
- 15px wide segments
- 33px tall thermometer
- Temperature range from -0.5 to 1.5

## Configuration

The mod works out-of-the-box with default settings. The following components can be configured:
- Stuck Arrows: Position, visibility
- Speedometer: Position, visibility
- Broken Block Tracker: Position, visibility
- Thermometer: Position, visibility

## Installation

1. Download the mod from CurseForge
2. Place the mod file in your `mods` folder
3. Launch Minecraft with Fabric Loader installed

## Compatibility

- Works with Fabric API
- Client-side only - no server installation needed

## Credits

- **Technical Director**: Aart
- **Developer**: aaddrick
- **Special Thanks**: [Squeek](https://github.com/squeek502) from Appleskin fame, from whom I shamelessly stole the bones of this mode from. 

## License

This mod is released under the [Unlicense](LICENSE.txt), meaning you're free to use, modify, and distribute it however you like!

