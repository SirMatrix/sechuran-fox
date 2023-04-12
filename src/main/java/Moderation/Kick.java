package Moderation;

import Handlers.Slash;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Objects;

public class Kick implements Slash {
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {

        Member uname = event.getOption("username", OptionMapping::getAsMember);
        String reas = event.getOption("reason", OptionMapping::getAsString);
        Guild guild = event.getGuild();
        Member bot = Objects.requireNonNull(guild, "Bot is null").getSelfMember();
        Member commandRunner = event.getMember();

        checkForRightPerms(commandRunner,bot,event);
    if(uname == null){
        event.reply("you can only kick a member").setEphemeral(true).queue();
        return;
    }
    if(!commandRunner.canInteract(uname)){
        event.reply("You are not allowed to kick someone who has more perms than you!").setEphemeral(true).queue();
        return;
    }

        guild.kick(uname).reason(reas).queue(
                s-> event.reply("Successfully kicked the member:" + uname.getEffectiveName() + "For the reason of: " + reas).queue(),
                e-> event.reply("Error kicking user form the discord! Error reason: " + e).queue()
        );



    }

    private void checkForRightPerms(Member commandRunner, Member bot, SlashCommandInteractionEvent event){
        if(!commandRunner.hasPermission(Permission.KICK_MEMBERS)){
            event.reply("You can't kick that user as you don't have the KICK_MEMBERS perm").queue();
            return;
        }
        if(!bot.hasPermission(Permission.KICK_MEMBERS)){
            event.reply("I can't kick the user as I dont have the KICK_MEMBERS perm").queue();
        }
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
