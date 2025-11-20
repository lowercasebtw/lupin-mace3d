package dev.foxgirl.mace3d;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record MaceModel(
    @NotNull Item item,
    @NotNull ModelResourceLocation normalModelLocation,
    @NotNull ModelResourceLocation inHandModelLocation
) {
    public ModelResourceLocation getLocation(boolean useInHandModel) {
        return useInHandModel ? Mace3D.MODEL.inHandModelLocation() : Mace3D.MODEL.normalModelLocation();
    }
}
