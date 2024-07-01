package tiny.grape.module.world;

import net.minecraft.block.BedBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SearchTags({"bed", "bed breaker"})
public class BedBreaker extends ModuleHandler {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Set<BlockPos> recentAttempts = new HashSet<>();

    public NumberSetting num = new NumberSetting("Radius", 1, 5, 3, 1);

    public BedBreaker() {
        super("Bed Breaker", Text.translatable("enderite.description.bedbreaker"), Category.WORLD);
        addSetting(num);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onTick() {
        super.onTick();
        breakNearbyBeds();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private void breakNearbyBeds() {
        if (mc.player == null || mc.world == null) {
            return;
        }

        BlockPos playerPos = mc.player.getBlockPos();
        int radius = num.getValueInt();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() instanceof BedBlock && !recentAttempts.contains(pos)) {
                        scheduleBreakBed(pos);
                    }
                }
            }
        }
    }

    private void scheduleBreakBed(BlockPos pos) {
        recentAttempts.add(pos);
        scheduler.schedule(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(getRandomDelay());

                mc.execute(() -> {
                    mc.player.swingHand(Hand.MAIN_HAND);
                });

                mc.execute(() -> mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, Direction.UP)));
                TimeUnit.MILLISECONDS.sleep(getBreakingTime());
                mc.execute(() -> mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP)));

                scheduler.schedule(() -> {
                    recentAttempts.remove(pos);
                }, 200, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getRandomDelay(), TimeUnit.MILLISECONDS);
    }

    private int getRandomDelay() {
        return 200 + random.nextInt(200);
    }

    private int getBreakingTime() {
        return 500 + random.nextInt(500);
    }
}