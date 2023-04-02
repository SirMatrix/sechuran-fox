package Handlers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public interface Slash {
    void onSlashCommandEvent(SlashCommandInteractionEvent event);
    String getName();

    String getDescription();

    boolean isSpecifiedGuildOnly();
    boolean isGuildOnly();

    default List<OptionData> getOptions(){
        return new ArrayList<>();
    }
    default CommandData getCommandData(){
        return new CommandDataImpl(getName(), getDescription())
                .setGuildOnly(isGuildOnly())
                .addOptions(getOptions());

    }


}
