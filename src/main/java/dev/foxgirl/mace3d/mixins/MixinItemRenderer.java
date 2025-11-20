package dev.foxgirl.mace3d.mixins;

import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

//? <=1.21.3 {
/*import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.foxgirl.mace3d.Mace3D;
import dev.foxgirl.mace3d.MaceModel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    //? <=1.21.3 {
    /*@Shadow
    @Final
    //? >=1.21.2 {
    private ModelManager modelManager;
    //?} else {
    *//*private ItemModelShaper itemModelShaper;
     *//*//?}

    @Unique
    private BakedModel mace3d$getBakedModelByResourceLocation(ModelResourceLocation modelResourceLocation) {
        //? >=1.21.2 {
        return this.modelManager.getModel(modelResourceLocation);
        //?} else {
        *//*return itemModelShaper.getModelManager().getModel(modelResourceLocation);
     *//*//?}
    }

    @Unique
    private BakedModel mace3d$getReplacementModel(ItemStack stack, boolean useInHandModel) {
        if (stack.is(Mace3D.MODEL.item())) {
            return mace3d$getBakedModelByResourceLocation(Mace3D.MODEL.getLocation(useInHandModel));
        } else {
            return null;
        }
    }

    @Inject(
        //? >=1.21.2 {
        method = "renderSimpleItemModel",
        //?} else {
        *//*method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
     *//*//?}
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
            ordinal = 0,
            shift = At.Shift.BEFORE
        )
    )
    private void mace3d$render(CallbackInfo info, @Local(ordinal = 0, argsOnly = true) ItemStack stack, @Local(ordinal = 0, argsOnly = true) LocalRef<BakedModel> modelRef) {
        final BakedModel model = mace3d$getReplacementModel(stack, false);
        if (model != null) {
            modelRef.set(model);
        }
    }

    @Inject(method = "getModel(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)Lnet/minecraft/client/resources/model/BakedModel;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", ordinal = 0, shift = At.Shift.BY, by = 2))
    private void mace3d$getModel(CallbackInfoReturnable<BakedModel> info, @Local(ordinal = 0, argsOnly = true) ItemStack stack, @Local(ordinal = 0) LocalRef<BakedModel> modelRef) {
        final BakedModel model = mace3d$getReplacementModel(stack, true);
        if (model != null) {
            modelRef.set(model);
        }
    }
    *///?}
}
