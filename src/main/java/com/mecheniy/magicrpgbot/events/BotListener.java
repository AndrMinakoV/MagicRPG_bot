package com.mecheniy.magicrpgbot.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;

@Mod.EventBusSubscriber
public class BotListener extends ListenerAdapter {

    private static MinecraftServer minecraftServer;

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
        minecraftServer = event.getServer();
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String componentId = event.getComponentId();

        switch (componentId) {
            case "playersList":
                sendPlayerList(event);
                break;
            case "restartServer":
                restartApplication(event);
                break;
            default:
                break;
        }
    }

    private void sendPlayerList(ButtonInteractionEvent event) {
        if (minecraftServer != null) {
            String playerNames = minecraftServer.getPlayerList().getPlayers().stream()
                    .map(player -> player.getGameProfile().getName())
                    .collect(Collectors.joining(", "));
            event.reply("Список игроков: " + playerNames).setEphemeral(true).queue();
        } else {
            event.reply("Сервер не доступен.").setEphemeral(true).queue();
        }
    }

    private void restartApplication(ButtonInteractionEvent event) {
        if (minecraftServer != null) {
            minecraftServer.stopServer();
            event.reply("Перезапускаю сервер...").setEphemeral(true).queue();
        } else {
            event.reply("Не могу перезапустить сервер: сервер не доступен.").setEphemeral(true).queue();
        }
    }
}
