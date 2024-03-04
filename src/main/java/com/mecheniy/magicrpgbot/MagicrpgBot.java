package com.mecheniy.magicrpgbot;
//ку Луприч я Стасян Кумысоед
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
    public static final String MOD_ID = "magicrpgbot";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static JDA jda;

    public MagicrpgBot() {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModListeners {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("test_onClientSetup");
            jda = startDiscordBot();
            if (jda != null) {
                // Теперь у нас есть экземпляр JDA, передайте его в ServerPlayerListUpdater
                ServerPlayerListUpdater updater = new ServerPlayerListUpdater(jda);
                List<String> dummyPlayerList = Arrays.asList("Player1", "Player2", "Player3"); // Это просто пример, замените на реальный список
                updater.sendPlayerListEmbed(dummyPlayerList);
            }
        }
    }

    private static JDA startDiscordBot() {
        try {
            JDABuilder builder = JDABuilder.createDefault("");
            builder.setActivity(Activity.watching("MagicRPG"));
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
}