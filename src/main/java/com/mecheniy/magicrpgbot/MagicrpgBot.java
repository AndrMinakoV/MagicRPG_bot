package com.mecheniy.magicrpgbot;
//ку Луприч я Стасян Кумысоед
import com.mecheniy.magicrpgbot.events.BotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.mecheniy.magicrpgbot.embed.ServerPlayerListUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

@Mod(MagicrpgBot.MOD_ID)
public class MagicrpgBot {
    private static final long startTime;

    static {
        // Сохраняем текущее время в миллисекундах как время старта
        startTime = System.currentTimeMillis();
    }
    public static final String MOD_ID = "magicrpgbot";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static JDA jda;

    public MagicrpgBot() {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModListeners {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Initializing client setup.");

            JDA jda = startDiscordBot(); // Инициализация Discord бота

            if (jda != null) {
                LOGGER.info("Discord bot started successfully.");

                ServerPlayerListUpdater updater = new ServerPlayerListUpdater(jda);

                // Здесь необходимо получить данные о сервере и игроках
                List<String> playerNames = Arrays.asList("Mecheniy");

                // Получение строки с временной меткой для Discord
                String uptimeMessage = getUptimeForDiscord();

                double tps = 20.0; // Пример значения TPS

                updater.sendPlayerListEmbed(playerNames, uptimeMessage, tps);
            } else {
                LOGGER.error("Failed to start Discord bot.");
            }
        }
        public static String getUptimeForDiscord() {
            // Получаем текущее время в секундах с начала эпохи Unix
            long nowSeconds = System.currentTimeMillis() / 1000;
            // Возвращаем временную метку для Discord
            return String.format("<t:%d:R>", nowSeconds);
        }


    private static JDA startDiscordBot() {
        try {
            JDABuilder builder = JDABuilder.createDefault("");
            builder.setActivity(Activity.watching("MagicRPG"));
            builder.addEventListeners(new BotListener());

            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            JDA jda = builder.build();
            jda.awaitReady(); // Подождать полной инициализации JDA
            LOGGER.info("Discord bot started successfully.");
            return jda;
        } catch (InterruptedException e) {
            LOGGER.error("Инициализация была прервана", e);
        }
        return null;
    }
}}