import Config.Config;
import Events.MessageListener;
import Events.ReadyListener;
import Events.TwitchListener;
import Handlers.SlashCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;


public class Main {

    static EnumSet<GatewayIntent> intents = EnumSet.of(
            // Enables MessageReceivedEvent for guild (also known as servers)
            GatewayIntent.GUILD_MESSAGES,
            // Enables the event for private channels (also known as direct messages)
            GatewayIntent.DIRECT_MESSAGES,
            // Enables access to message.getContentRaw()
            GatewayIntent.MESSAGE_CONTENT,
            // Enables MessageReactionAddEvent for guild
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            // Enables MessageReactionAddEvent for private channels
            GatewayIntent.DIRECT_MESSAGE_REACTIONS
    );
    public  static void main(String[] args) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(Config.get("TOKEN"), intents)
                .setActivity(Activity.playing("Digging a hole!"))
                .build();
        jda.awaitReady().addEventListener(new ReadyListener(), new MessageListener(),new SlashCommandHandler(jda, jda.getGuildById("467422168339316754")));



    }

}
