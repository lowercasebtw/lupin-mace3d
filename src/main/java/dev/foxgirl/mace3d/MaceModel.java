package dev.foxgirl.mace3d;

//? >=1.21.4 {
import net.minecraft.resources.ResourceLocation;
//?} else {
/*import net.minecraft.client.resources.model.ModelResourceLocation;
 *///?}

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record MaceModel(
    @NotNull Item item,
    //? >=1.21.4 {
    @NotNull ResourceLocation location
    //?} else {
    /*@NotNull ModelResourceLocation normalModelLocation,
    @NotNull ModelResourceLocation inHandModelLocation
    *///?}
) {
    //? <=1.21.3 {
    /*public ModelResourceLocation getLocation(boolean useInHandModel) {
        return useInHandModel ? Mace3D.MODEL.inHandModelLocation() : Mace3D.MODEL.normalModelLocation();
    }
    *///?}
}
