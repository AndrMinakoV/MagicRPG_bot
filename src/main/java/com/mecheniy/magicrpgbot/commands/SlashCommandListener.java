package com.mecheniy.magicrpgbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;

public class SlashCommandListener extends ListenerAdapter {
    private static MinecraftServer minecraftServer;

    public void onSlashCommand(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ex")) {
            String command = event.getOption("command").getAsString();

            // Проверяем, есть ли сервер
            if (minecraftServer != null) {
                // Обработка различных команд
                CommandSourceStack source = minecraftServer.createCommandSourceStack();
                minecraftServer.getFunctions().execute(command, source);
                minecraftServer.getCommands().performCommand(source, command)
                // Получаем список игроков и формируем ответ
                String playerList = String.join(", ", minecraftServer.getPlayerList().getPlayers().stream().map(player -> player.getDisplayName().getString()).collect(Collectors.toList()));
                event.reply("Список игроков на сервере: " + playerList).setEphemeral(true).queue();
            } else {
                    // Для других команд, отправляем их на сервер
                minecraftServer.getCommands().performCommand(minecraftServer.createCommandSourceStack(), command);


                event.reply("Команда выполнена: " + command).setEphemeral(true).queue();
                }
            } else {
                event.reply("Не могу выполнить команду: сервер не доступен.").setEphemeral(true).queue();
            }
        }
    }
}