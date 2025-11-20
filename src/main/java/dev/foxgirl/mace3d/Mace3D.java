package dev.foxgirl.mace3d;

//? fabric {
/*import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
*///?} else neoforge {
import net.neoforged.fml.common.Mod;
 //?}

//? <=1.21.3 {
/*import net.minecraft.client.resources.model.ModelResourceLocation;
*///?}

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

//? neoforge
@Mod(Mace3D.MOD_ID)
//? fabric
/*@Entrypoint*/
public final class Mace3D
    //? fabric
    /*implements ClientModInitializer*/
{
    public static final String MOD_ID = "@MODID@";

    public static final MaceModel MODEL = new MaceModel(
        Items.MACE,
        //? >= 1.21.4 {
        location("mace")
        //?} else {
        /*new ModelResourceLocation(ResourceLocation.withDefaultNamespace("mace"), "inventory"),
        new ModelResourceLocation(location("mace_in_hand"), "inventory")
        *///?}
    );

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    //? fabric {
    /*@Override
    public void onInitializeClient() {
    }
    *///?}
}
