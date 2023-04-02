package GenericCommands;

import Handlers.Slash;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;


public class RandomFox implements Slash {

    private static final String API_URL = "https://randomfox.ca/floof/";
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            String imageUrl = jsonObject.getString("image");
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Random Fox Image")
                    .setImage(imageUrl);
            event.replyEmbeds(embedBuilder.build()).queue();
        } catch (IOException | InterruptedException | JSONException e) {
            event.reply("Failed to generate fox image. Please try again later.").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "randomfox";
    }

    @Override
    public String getDescription() {
        return "Will show a random fox image!";
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
