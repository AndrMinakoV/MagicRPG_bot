package com.mecheniy.magicrpgbot.embed;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.awt.Color;
import java.util.List;
//import io.github.cdimascio.dotenv.Dotenv;
public class ServerPlayerListUpdater {

    private JDA jda;
    private String channelId;

    public ServerPlayerListUpdater(JDA jda) {
        // Dotenv dotenv = Dotenv.load();
        this.jda = jda;
        // Предполагается, что CHANNEL_ID это имя переменной среды, в которой хранится ID Discord канала
        this.channelId = "1214243958083821689";
    }

    public void sendPlayerListEmbed(List<String> playerNames) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            System.err.println("Канал не найден. Убедитесь, что CHANNEL_ID верный.");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Список игроков на сервере");
        embed.setColor(Color.BLUE);
        StringBuilder description = new StringBuilder();

        if (playerNames.isEmpty()) {
            description.append("На сервере нет игроков.");
        } else {
            for (String playerName : playerNames) {
                description.append(playerName).append("\n");
            }
        }

        embed.setDescription(description.toString());
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
