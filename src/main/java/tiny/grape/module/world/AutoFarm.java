package tiny.grape.module.world;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"farm", "auto farm", "auto harvest"})
public class AutoFarm extends ModuleHandler {
    public NumberSetting range = new NumberSetting("Range", 1f, 5f, 4f, 1f);
    private final String moduleName = "Auto Farm";
    private final KeyBindSetting keyBindSetting;

    public AutoFarm() {
        super("Auto Farm", Text.translatable("enderite.description.autofarm"), Category.WORLD);
        addSetting(range);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
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

                    BlockState blockState = client.world.getBlockState(currentPos);

                    // Harvest sugar cane
                    if (blockState.getBlock() == Blocks.SUGAR_CANE) {
                        BlockPos belowPos = currentPos.down();
                        if (client.world.getBlockState(belowPos).getBlock() == Blocks.SUGAR_CANE) {
                            BlockPos twoBelowPos = currentPos.down(2);
                            if (!(client.world.getBlockState(twoBelowPos).getBlock() == Blocks.SUGAR_CANE)) {
                                assert client.interactionManager != null;
                                client.interactionManager.updateBlockBreakingProgress(currentPos, breakDirection);
                            }
                        }
                    }

                    // Harvest fully grown crops
                    if (blockState.getBlock() instanceof CropBlock) {
                        if (blockState.getBlock() instanceof BeetrootsBlock) {
                            if (blockState.get(Properties.AGE_3) == 3) {
                                assert client.interactionManager != null;
                                client.interactionManager.updateBlockBreakingProgress(currentPos, breakDirection);
                            }
                        } else if (blockState.get(Properties.AGE_7) == 7) {
                            assert client.interactionManager != null;
                            client.interactionManager.updateBlockBreakingProgress(currentPos, breakDirection);
                        }
                    }

                    // Attempt to plant seeds
                    tryPlant(client, currentPos);
                }
            }
        }
    }

    private void tryPlant(MinecraftClient client, BlockPos pos) {
        BlockState blockState = client.world.getBlockState(pos);
        if (blockState.getBlock() == Blocks.FARMLAND) {
            BlockPos abovePos = pos.up();
            BlockState blockStateUp = client.world.getBlockState(abovePos);
            if (blockStateUp.getBlock() == Blocks.AIR) {
                if (tryUseSeed(client, abovePos, Hand.MAIN_HAND) || tryUseSeed(client, abovePos, Hand.OFF_HAND)) {
                }
            }
        }
    }

    private boolean tryUseSeed(MinecraftClient client, BlockPos pos, Hand hand) {
        if (client.player == null) {
            return false;
        }

        Item item = client.player.getStackInHand(hand).getItem();
        if (item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.POTATO ||
                item == Items.CARROT || item == Items.MELON_SEEDS || item == Items.PUMPKIN_SEEDS) {

            Vec3d blockPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            BlockHitResult hit = new BlockHitResult(blockPos, Direction.UP, pos, false);
            client.interactionManager.interactBlock(client.player, hand, hit);
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }
}
