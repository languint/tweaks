package longuint.tweaks.mixin;


import net.minecraft.entity.passive.MerchantEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin {

    @Inject(method = "canBeLeashed", at = @At("HEAD"), cancellable = true)
    private void canBeLeashed(@NotNull CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}