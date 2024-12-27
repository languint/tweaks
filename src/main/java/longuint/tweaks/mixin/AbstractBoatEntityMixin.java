package longuint.tweaks.mixin;

import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoatEntity.class)
public abstract class AbstractBoatEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void modifyBoatVelocity(CallbackInfo ci) {
        AbstractBoatEntity boat = (AbstractBoatEntity) (Object) this;

        if (!boat.isTouchingWater()) return;

        boolean isRowing = boat.isPaddleMoving(0) || boat.isPaddleMoving(1);

        Vec3d velocity = boat.getVelocity();

        if (isRowing) {
            double horizontalMultiplier = 1.2;
            double maxSpeed = 1.0;

            double currentSpeed = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);

            if (currentSpeed < maxSpeed) {
                double newX = velocity.x * horizontalMultiplier;
                double newZ = velocity.z * horizontalMultiplier;

                boat.setVelocity(newX, velocity.y, newZ);
            }
        } else {
            double dragFactor = 0.9;
            double newX = velocity.x * dragFactor;
            double newZ = velocity.z * dragFactor;

            boat.setVelocity(newX, velocity.y, newZ);
        }
    }
}
