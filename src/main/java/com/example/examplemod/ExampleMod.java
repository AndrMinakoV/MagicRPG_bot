package com.example.examplemod;
//ку Луприч я Стасян Кумысоед
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    public ExampleMod()

    {
        startDiscordBot();

        // Register ourselves for server and other game events we are interested in
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Initialization code for your mod
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Code to execute when the server starts
        LOGGER.info("HELLO from server starting");

    }

    private void startDiscordBot() {

            JDABuilder builder = JDABuilder.createDefault("");
            builder.setActivity(Activity.watching("MagicRPG 1.19.2"));
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            builder.build();
            LOGGER.info("Discord bot started successfully.");

    }
}
