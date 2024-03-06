package com.mecheniy.magicrpgbot.embed;

import com.mecheniy.magicrpgbot.buttons.ButtonManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

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
    public class GetUpTime {

        public static void main(String... args) throws InterruptedException {
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();

            System.out.println("Up time: " + rb.getUptime() + " ms");
            Thread.sleep(1000 * 10);
            System.out.println("Up time: " + rb.getUptime() + " ms");
        }
    }
    public void sendPlayerListEmbed(List<String> playerNames, String uptimeMessage, double tps) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            System.err.println("Канал не найден. Убедитесь, что CHANNEL_ID верный.");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        //buttons
        Button playersListButton = ButtonManager.createPlayersListButton();
        Button restartServerButton = ButtonManager.createRestartServerButton();

        //

        embed.setTitle("MagicRPG 1.19.2");

        // Так как мы уже передаем отформатированное строковое сообщение, нам не нужно вызывать formatUptime
        embed.addField("Последний рестарт", uptimeMessage, true);
        embed.addField("TPS", String.valueOf(tps), true); // TPS как строку
        // Если вы хотите добавить количество игроков в качестве поля:
        embed.addField("Игроков на сервере", playerNames.isEmpty() ? "нет" : String.valueOf(playerNames.size()), true);

        // Добавление имен игроков в описание, если это необходимо
        /* if (!playerNames.isEmpty()) {
            StringBuilder description = new StringBuilder();
            for (String playerName : playerNames) {
                description.append(playerName).append("\n");
            }
            embed.setDescription("Никнеймы игроков: " + description.toString());
        } else {
            embed.setDescription("На сервере нет игроков.");
        }*/

        embed.setColor(Color.darkGray);

        channel.sendMessageEmbeds(embed.build())
                .setActionRow(playersListButton, restartServerButton)
                .queue();;

    }

    // Метод для форматирования времени аптайма
    private String formatUptime(long uptimeMillis) {
        long uptimeSeconds = uptimeMillis / 1000;
        long hours = uptimeSeconds / 3600;
        long minutes = (uptimeSeconds % 3600) / 60;
        long seconds = uptimeSeconds % 60;

        return String.format("%d часов %d минут %d секунд", hours, minutes, seconds);

    }
}

