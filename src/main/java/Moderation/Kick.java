package Moderation;

import Handlers.Slash;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Objects;

public class Kick implements Slash {
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {

        String uname = event.getOption("username").getAsString();
        Member memberToKick = (Member) event.getOption("username" ).getAsMember();
        String reas = event.getOption("reason").getAsString();
        Guild guild = event.getGuild();
        Member bot = (Member) Objects.requireNonNull(guild, "Not is null!").getSelfMember();
        Member commandInit = (Member) event.getMember();





    }



    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick a user from the server";
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
      return List.of(
              new OptionData(OptionType.USER, "username", "The user to kick.")
                      .setRequired(true),
              new OptionData(OptionType.STRING, "reason", "The reason for kicking.")
                      .setRequired(true)
      );
    }
}
