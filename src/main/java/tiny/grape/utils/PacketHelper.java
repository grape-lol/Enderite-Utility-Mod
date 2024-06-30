package tiny.grape.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import tiny.grape.mixin.ClientConnectionInvoker;

public class PacketHelper {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    // Positon packet
    public static void sendPosition(Vec3d pos) {
        if (client.player != null && client.getNetworkHandler() != null) {
            ClientConnectionInvoker conn = (ClientConnectionInvoker) client.getNetworkHandler().getConnection();
            pos = fixCoords(pos);
            PlayerMoveC2SPacket packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
            conn.voidSendIm(packet, null, true);
        }
    }

    public static Vec3d fixCoords(Vec3d pos) {
        return pos;
    }
}
