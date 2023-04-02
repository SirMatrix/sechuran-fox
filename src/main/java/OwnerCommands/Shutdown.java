package OwnerCommands;

import Config.Config;
import Handlers.Slash;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Shutdown implements Slash {
   String authId = "187571803827339265";
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        String userId = user.getId();
        if(userId.equals(authId))
        {
            event.reply("Shutting down the bot!").queue();
            event.getJDA().shutdown();
        }else{
            event.reply("Your are not the bot owner!").queue();
        }
    }

    @Override
    public String getName() {
        return "shutdown";
    }

    @Override
    public String getDescription() {
        return "Shutdown the bot";
    }

    @Override
    public boolean isSpecifiedGuildOnly() {
        return true;
    }

    @Override
    public boolean isGuildOnly() {
        return false;
    }
}
