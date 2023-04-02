package GenericCommands;

import Handlers.Slash;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Ping implements Slash {
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
    event.reply("Pong").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Returns with Pong";
    }

    @Override
    public boolean isSpecifiedGuildOnly() {
        return false;
    }

    @Override
    public boolean isGuildOnly() {
        return false;
    }
}
