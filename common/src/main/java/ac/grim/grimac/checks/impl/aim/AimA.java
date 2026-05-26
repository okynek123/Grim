package me.tecnio.ahm.check.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;

/**
 * Check to detect rotations without a rotation constant.
 */
@CheckData(name = "AimA", stableKey = "grim.aim.aim_a")
public final class AimA extends Check implements RotationCheck {

    public AimA(final PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final RotationUpdate update) {
        if (this.isExempt(ExemptType.AIM, ExemptType.TELEPORT)) return;

        final float constant = data.getRotationTracker().getSensitivity() / 142.0F;

        final float pitch = update.getPitch();
        final float yaw = update.getYaw();

        final float moduloPitch = Math.abs(pitch % constant);
        final float moduloYaw = Math.abs(yaw % constant);

        if (moduloPitch == 0.0D && moduloYaw == 0.0D) {
            if (this.buffer.increase() > 30) {
                this.fail("mP: " + moduloPitch);
            }
        } else {
            this.buffer.decreaseBy(0.05D);
        }
    }
}
