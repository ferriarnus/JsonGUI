package com.ferriarnus.jsonguis.mixin;

import com.ferriarnus.jsonguis.JsonGUIS;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @ModifyVariable(at = @At(value = "LOAD"), method = "setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", argsOnly = true)
    private static ResourceLocation changeRL(ResourceLocation rl) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.containerMenu == player.inventoryMenu) {
            return rl;
        }
        try {
            MenuType<?> type = player.containerMenu.getType();
            return JsonGUIS.TEXTURE_JSON.changeTextureIfNeeded(ForgeRegistries.MENU_TYPES.getKey(type), rl);
        } catch (UnsupportedOperationException e) {

        }
        return rl;
    }
}
