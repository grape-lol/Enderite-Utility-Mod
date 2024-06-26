/*
 * Copyright (c) 2014-2024 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

// damn still doesnt bypass 😭

package tiny.grape.module.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;

public class Flight extends ModuleHandler {
    public NumberSetting kickInt = new NumberSetting("Anti-Kick Interval", 5, 80, 58, 1.0);
    public NumberSetting kickDist = new NumberSetting("Anti-Kick Distance", 0.01, 0.2, 0.05, 0.001);
    public NumberSetting forwardSpeed = new NumberSetting("Forward Speed", 0.05, 5, 1, 0.05);
    public NumberSetting vertSpeed = new NumberSetting("Vertical Speed", 0.05, 5, .2, 0.05);
    public NumberSetting horzSpeed = new NumberSetting("Horizontal Speed", 0.05, 5, .2, 0.05);

    private int tickCounter = 0;

    public Flight() {
        super("Flight", "bro is superman", Category.MOVEMENT);
        addSetting(kickInt);
        addSetting(kickDist);
        addSetting(forwardSpeed);
        addSetting(vertSpeed);
        addSetting(horzSpeed);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onEnable() {
        tickCounter = 0;
        super.onEnable();
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = client.player;

        player.getAbilities().flying = true;

        Vec3d velocity = player.getVelocity();
        double forwardSpeedValue = forwardSpeed.getValue();

        player.setVelocity(0, velocity.y, 0);

        if(client.options.jumpKey.isPressed()) {
            player.setVelocity(velocity.x, vertSpeed.getValue(), velocity.z);
        } else if(client.options.sneakKey.isPressed()) {
            player.setVelocity(velocity.x, -vertSpeed.getValue(), velocity.z);
        }

        if(client.options.forwardKey.isPressed()) {
            Vec3d forward = Vec3d.fromPolar(0, player.getYaw()).normalize().multiply(forwardSpeedValue);
            player.addVelocity(forward.x, 0, forward.z);
        }

        doAntiKick(velocity);
        super.onTick();
    }



    private void doAntiKick(Vec3d velocity) {
        if (tickCounter > kickInt.getValueInt() + 1)
            tickCounter = 0;

        switch (tickCounter) {
            case 0:
                if (client.options.sneakKey.isPressed())
                    tickCounter = 2;
                else
                    client.player.setVelocity(-velocity.x, -kickDist.getValue(), -velocity.z);
                break;

            case 1:
                client.player.setVelocity(-velocity.x, -kickDist.getValue(), -velocity.z);
                break;
        }

        tickCounter++;
    }


    @Override
    public void onDisable() {
        client.player.getAbilities().flying = false;
        super.onDisable();
    }
}
