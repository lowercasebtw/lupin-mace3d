package dev.foxgirl.mace3d.mixins;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.Item;

//? >=1.21.4 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.foxgirl.mace3d.Mace3D;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.At;
//?}

@Mixin(Item.class)
public abstract class MixinItem {
    //? >=1.21.4 {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;effectiveModel()Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation mace3d$replaceMaceModel(Item.Properties instance, Operation<ResourceLocation> original) {
        final ResourceLocation resourceLocation = original.call(instance);
        if (resourceLocation.equals(ResourceLocation.withDefaultNamespace("mace"))) {
            return Mace3D.MODEL.location();
        } else {
            return resourceLocation;
        }
    }
    //?}
}
