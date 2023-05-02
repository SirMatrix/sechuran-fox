package Warframe;

import Handlers.WFData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;

public class WarframParse {
    private  static final  String BASE_URL = "https://api.warframestat.us/warframes/";

    public static WFData retriveWFData(String wfName){
        String WFUrl = BASE_URL + wfName.replaceAll("\\s+", "%20") + "/";
        WFData wfData = null;
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(WFUrl))
                .GET()
                .build();

        try{
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONObject wframeJson = new JSONObject(responseBody);
            String name = wframeJson.getString("name");
            String description = wframeJson.getString("description");
            JSONArray abilities = wframeJson.getJSONArray("abilities");
            String[] abils = new String[abilities.length()];
            for(int i = 0; i < abilities.length(); i++)
            {
                JSONObject ability = abilities.getJSONObject(i);
                String names = ability.getString("name");
                abils[i] = names;

            }
            //System.out.println(pols);
            int power = wframeJson.getInt("power");
            int shield = wframeJson.getInt("shield");
            String relDate = wframeJson.getString("releaseDate");
            double sprintSpeed = wframeJson.getDouble("sprintSpeed");
            int health = wframeJson.getInt("health");
            String wFrameImage = wframeJson.getString("wikiaThumbnail");

            wfData = new WFData(name, description, power, shield, relDate, sprintSpeed, health, wFrameImage, abils);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return wfData;
    }
}
