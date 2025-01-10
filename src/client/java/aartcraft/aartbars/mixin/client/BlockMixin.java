package aartcraft.aartbars.mixin.client;

import aartcraft.aartbars.client.components.brokenblocktracker.BrokenBlockTrackerEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Unique
    private static int blocksBroken = 0;

    @Inject(method = "onBroken", at = @At("HEAD"))
    private void onBlockBroken(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (world.isClient()) {
            blocksBroken++;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null) {
                // Create and fire the event
                BrokenBlockTrackerEvent.EVENT.invoker().interact(
                    new BrokenBlockTrackerEvent(
                        blocksBroken,
                        0, 0, // These coordinates will be overridden by the component
                        new DrawContext(client, client.getBufferBuilders().getEntityVertexConsumers())
                    )
                );
            }
        }
    }
}
