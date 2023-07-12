package com.ferriarnus.jsonguis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static com.ferriarnus.jsonguis.ClientConfig.CONTAINERNAMES;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    private static MenuType<?> type;

    @SubscribeEvent
    static void container(ContainerScreenEvent event) {
        if (CONTAINERNAMES.get()) {
            AbstractContainerMenu type = event.getContainerScreen().getMenu();
            LocalPlayer player = Minecraft.getInstance().player;
            try {
                type.getType();
            } catch (UnsupportedOperationException e) {
                return;
            }
            if (type != player.inventoryMenu && type.getType() != ClientEvents.type) {
                if (player.containerMenu instanceof ChestMenu chestMenu) {
                    player.sendSystemMessage(Component.literal(chestMenu.getContainer().getClass().toString()));
                }
                player.sendSystemMessage(Component.literal(ForgeRegistries.MENU_TYPES.getKey(type.getType()).toString()));
                ClientEvents.type = type.getType();
            }
        }
    }
}
