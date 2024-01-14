package com.example.examplemod;
//ку Луприч я Стасян Кумысоед
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Mod(ExampleMod.MOD_ID)
public class ExampleMod {
    public static final String MOD_ID = "examplemod";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public ExampleMod() {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModListeners {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("test_onClientSetup");
            startDiscordBot();
        }
    }

    private static void startDiscordBot() {
        JDABuilder builder = JDABuilder.createDefault("");//
        builder.setActivity(Activity.watching("MagicRPG 1.19.2"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.build();
        LOGGER.info("Discord bot started successfully.");

    }
}
