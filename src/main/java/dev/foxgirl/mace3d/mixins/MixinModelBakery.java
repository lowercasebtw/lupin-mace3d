package dev.foxgirl.mace3d.mixins;

import net.minecraft.client.resources.model.ModelBakery;
import org.spongepowered.asm.mixin.Mixin;

//? <=1.21.1 {
/*import dev.foxgirl.mace3d.Mace3D;
import dev.foxgirl.mace3d.MaceModel;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Map;
*///?}

@Mixin(ModelBakery.class)
public abstract class MixinModelBakery {
    //? <=1.21.1 {
    /*@Shadow
    private void loadSpecialItemModelAndDependencies(ModelResourceLocation id) {
        throw new AssertionError();
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery;loadSpecialItemModelAndDependencies(Lnet/minecraft/client/resources/model/ModelResourceLocation;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void mace3d$loadSpecialItemModelAndDependencies(BlockColors blockColors, ProfilerFiller profilerFiller, Map<?, ?> modelResources, Map<?, ?> blockStateResources, CallbackInfo ci) {
        loadSpecialItemModelAndDependencies(Mace3D.MODEL.normalModelLocation());
        loadSpecialItemModelAndDependencies(Mace3D.MODEL.inHandModelLocation());
    }
    *///?}

    /*
    @Inject(
        method = "<init>", at = @At(
            value = "INVOKE", ordinal = 0,
            target = "Lnet/minecraft/client/resources/model/ModelBakery;loadSpecialItemModelAndDependencies(Lnet/minecraft/client/resources/model/ModelResourceLocation;)V"
        )
    )
    // @Inject(
    //     method = "<init>(Lnet/minecraft/client/color/block/BlockColors;Lnet/minecraft/util/profiling/ProfilerFiller;Ljava/util/Map;Ljava/util/Map;)V",
    //     at = @At("TAIL")
    // )
    private void mace3d$injected$__init__(CallbackInfo info) {
        for (var model : Mace3D.getMaceModels()) {
            Mace3D.LOGGER.info("loadSpecialItemModelAndDependencies({})", model.normalModelResourceLocation);
            loadSpecialItemModelAndDependencies(model.normalModelResourceLocation);
            Mace3D.LOGGER.info("loadSpecialItemModelAndDependencies({})", model.inHandModelResourceLocation);
            loadSpecialItemModelAndDependencies(model.inHandModelResourceLocation);
        }
    }
    */
}
