package OwnerCommands;

import Config.Config;
import Config.ConfigUploader;
import Config.SQLDriver;
import Handlers.Slash;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;

public class UnrestrictCommand implements Slash {

    SQLDriver sqlDriver = new SQLDriver(Config.get("DB_URL"), Config.get("DB_USER"), Config.get("DB_PASS"));
    ConfigUploader configUploader = new ConfigUploader(sqlDriver);


    public UnrestrictCommand(ConfigUploader configUploader,  SQLDriver sqlDriver) {
        this.configUploader = configUploader;
        this.sqlDriver = sqlDriver;
    }
    public UnrestrictCommand(){

    }
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
    if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)){
        event.reply("You do not have permission to use this command!").setEphemeral(true).queue();
        }
        // Get command name from user input
        String commandName = event.getOption("command").getAsString();
        String serverId = event.getGuild().getId();
        // Unrestrict command
        configUploader.removeCommandRestriction(serverId, commandName);

        // Send confirmation message
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Command Unrestricted")
                .setDescription("The command `" + commandName + "` has been unrestricted.")
                .setColor(Color.GREEN);

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }

    @Override
    public String getName() {
        return "unrestrict";
    }

    @Override
    public String getDescription() {
        return "Remove a channel restricted command!";
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
                        .setRequired(true)
        );
    }
}
