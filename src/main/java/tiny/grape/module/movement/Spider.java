package tiny.grape.module.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;

@SearchTags({"spider","wall walk","wallwalk"})
public class Spider extends ModuleHandler {
    public Spider() {
        super("Spider", Text.translatable("enderite.description.spider"), Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = client.player;
        if(!player.horizontalCollision)
            return;

        Vec3d velocity = player.getVelocity();
        if(velocity.y >= 0.2)
            return;

        player.setVelocity(velocity.x, 0.2, velocity.z);
        super.onTick();
    }
}
