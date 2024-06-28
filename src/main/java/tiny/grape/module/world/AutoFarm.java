package tiny.grape.module.world;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;

@SearchTags({"farm", "auto farm", "auto harvest"})
public class AutoFarm extends ModuleHandler {
    public NumberSetting range = new NumberSetting("Range", 1f, 5f, 4f, 1f);

    public AutoFarm() {
        super("Auto Farm", "Im a farmer not a miner.", Category.WORLD);
        addSetting(range);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onTick() {
        super.onTick();

        if (client == null || client.world == null || client.player == null) {
            return;
        }

        int radius = (int) range.getValue();
        BlockPos playerPos = BlockPos.ofFloored(client.player.getPos());
        Direction breakDirection = Direction.UP;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos currentPos = playerPos.add(x, y, z);

                    if (client.world.getBlockState(currentPos).getBlock() == Blocks.SUGAR_CANE) {
                        BlockPos belowPos = currentPos.down();
                        if (client.world.getBlockState(belowPos).getBlock() == Blocks.SUGAR_CANE) {
                            BlockPos twoBelowPos = currentPos.down(2);
                            if (!(client.world.getBlockState(twoBelowPos).getBlock() == Blocks.SUGAR_CANE)) {
                                assert client.interactionManager != null;
                                client.interactionManager.updateBlockBreakingProgress(currentPos, breakDirection);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}