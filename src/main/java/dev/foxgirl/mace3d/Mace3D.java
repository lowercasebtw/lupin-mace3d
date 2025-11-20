package dev.foxgirl.mace3d;

//? fabric {
/*import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
*///?} else neoforge {
import net.neoforged.fml.common.Mod;
//?}

import net.minecraft.client.resources.model.ModelResourceLocation;
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
        ModelResourceLocation.inventory(ResourceLocation.withDefaultNamespace("mace")),
        ModelResourceLocation.inventory(location("mace_in_hand"))
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
