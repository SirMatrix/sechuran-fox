package OwnerCommands;

import Config.Config;
import Config.ConfigUploader;
import Config.SQLDriver;
import Handlers.Slash;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;

public class ChannelRestrictedCommand implements Slash {

    SQLDriver sqlDriver = new SQLDriver(Config.get("DB_URL"), Config.get("DB_USER"), Config.get("DB_PASS"));
    ConfigUploader configUploader = new ConfigUploader(sqlDriver);

    public ChannelRestrictedCommand(ConfigUploader configUploader, SQLDriver sqlDriver) {
        this.configUploader = configUploader;
        this.sqlDriver = sqlDriver;
    }
    public ChannelRestrictedCommand() {}
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        String serverId = event.getGuild().getId();
        String commandName = event.getOption("command").getAsString();
        String channelMention = event.getOption("channel").getAsString();
        String channelId = channelMention.replaceAll("\\D+", "");
        configUploader.saveConfig(serverId, commandName, channelId);


        MessageEmbed reply = new EmbedBuilder()
                .setTitle("Command Restricted")
                .setDescription(String.format("The command `%s` has been restricted to the channel %s.", commandName, channelMention))
                .setColor(Color.GREEN)
                .build();

        event.replyEmbeds(reply).queue();

    }

    @Override
    public String getName() {
        return "restrict";
    }

    @Override
    public String getDescription() {
        return "Restrict a command to a specific channel!";
    }

    @Override
    public boolean isSpecifiedGuildOnly() {
        return false;
    }

    @Override
    public boolean isGuildOnly() {
        return true;
    }

    @Override
    public List<OptionData> getOptions() {
        return  List.of(
                new OptionData(OptionType.STRING, "command", "The name of the command to restrict")
                        .setRequired(true),
                new OptionData(OptionType.CHANNEL, "channel", "The channel to restrict the command to.")
                        .setRequired(true)
        );
    }
}

