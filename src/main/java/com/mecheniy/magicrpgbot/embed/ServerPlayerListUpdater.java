package com.mecheniy.magicrpgbot.embed;

import com.mecheniy.magicrpgbot.buttons.ButtonManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

//import io.github.cdimascio.dotenv.Dotenv;
public class ServerPlayerListUpdater {

    private JDA jda;
    private String channelId;
    private String threadId;

    public ServerPlayerListUpdater(JDA jda) {
        // Dotenv dotenv = Dotenv.load();
        this.jda = jda;
        // Предполагается, что CHANNEL_ID это имя переменной среды, в которой хранится ID Discord канала
        this.channelId = "1222131081210499073";
        this.threadId = "1222183057239314582";
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

        // Предполагаем, что вы уже получили threadId каким-то образом
        ThreadChannel thread = channel.getGuild().getThreadChannelById(threadId); // ID ветки
        if (thread == null) {
            System.err.println("Ветка не найдена. Убедитесь, что THREAD_ID верный.");
            return;
        }
        Button playersListButton = ButtonManager.createPlayersListButton();
        Button restartServerButton = ButtonManager.createRestartServerButton();
        // Создание EmbedBuilder
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("EnigmaRPG 1.19.2");
        embed.addField("Последний рестарт", uptimeMessage, true);
        embed.addField("TPS", String.valueOf(tps), true);
        embed.addField("Игроков на сервере", playerNames.isEmpty() ? "нет" : String.valueOf(playerNames.size()), true);
        embed.setColor(Color.darkGray);

        // Поиск предыдущего сообщения от бота
        thread.getIterableHistory().takeAsync(100).thenAccept(messages -> {
            Message toEdit = messages.stream()
                    .filter(msg -> msg.getAuthor().equals(jda.getSelfUser()) && !msg.getEmbeds().isEmpty()) // Проверяем, что это embed сообщение от бота
                    .findFirst()
                    .orElse(null);

            if (toEdit != null) {
                // Редактирование найденного сообщения новым embed
                toEdit.editMessageEmbeds(embed.build()).setActionRow(playersListButton, restartServerButton).queue();
            } else {
                // Отправка нового сообщения, если подходящее сообщение не найдено
                thread.sendMessageEmbeds(embed.build())   .setActionRow(playersListButton, restartServerButton).queue();
            }
        });
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

