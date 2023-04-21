package Moderation;

import Handlers.Slash;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Ban implements Slash {

    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        Member uName = event.getOption("username", OptionMapping::getAsMember);
        String reason = event.getOption("reason", OptionMapping::getAsString);
        Integer del = event.getOption("delete", OptionMapping::getAsInt);
        Guild guild = event.getGuild();
        Member bot = Objects.requireNonNull(guild, "Bot is null").getSelfMember();
        Member commandRunner = event.getMember();

        checkForRightPerms(commandRunner,bot,event);
        if(uName == null){
            event.reply("you can only kick a member").setEphemeral(true).queue();
            return;
        }
        if(!commandRunner.canInteract(uName)){
            event.reply("You are not allowed to kick someone who has more perms than you!").setEphemeral(true).queue();
            return;
        }

        guild.ban(uName,del, TimeUnit.DAYS).reason(reason).queue();

    }

    private void  checkForRightPerms(Member commandRunner, Member bot, SlashCommandInteractionEvent event){
        if(!commandRunner.hasPermission(Permission.BAN_MEMBERS)){
            event.reply("You can't kick that user as you don't have the perms to do so!").setEphemeral(true).queue();
            return;
        }
        if(!bot.hasPermission(Permission.BAN_MEMBERS)){
            event.reply("I cant kick that user as i dont have the perms to do so!").setEphemeral(true).queue();
        }
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Ban a user from the guild and delete messages from a certain time frame";
    }

    @Override
    public boolean isSpecifiedGuildOnly() {
        return false;
    }

    @Override
    public boolean isGuildOnly() {
        return false;
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
          new OptionData(OptionType.USER, "username", "Username to ban from the discord server")
                  .setRequired(true),
          new OptionData(OptionType.STRING, "reason", "Reason for the ban")
                  .setRequired(true),
                new OptionData(OptionType.INTEGER, "delete", "Amount of messages to delete")
        );

    }
}
