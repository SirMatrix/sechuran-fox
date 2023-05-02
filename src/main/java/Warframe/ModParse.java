package Warframe;

import Handlers.Drop;
import Handlers.ModData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ModParse {
    private static final String BASE_URL = "https://api.warframestat.us/mods/search/";


    public static ModData retrieveModData(String modName){
        String name = "";
        String rarity = "";
        String thumbnail = "";
        String polarity = "";
        String type2 = "";
        boolean isAugment = false;

        String modUrl = BASE_URL + modName.replaceAll("\\s+", "%20" ) + "/";
        ModData modData = null;

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(modUrl))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONArray modJson = new JSONArray(responseBody);
            List<Drop> dropList = new ArrayList<>();
            for(int i = 0; i<modJson.length(); i++){
                JSONObject modObj = modJson.getJSONObject(i);
                 name = modObj.getString("name");
                 rarity = modObj.getString("rarity");
                 thumbnail = modObj.getString("wikiaThumbnail");
                polarity = modObj.getString("polarity");
                type2 = modObj.getString("type");
                System.out.println(name +  rarity + isAugment);
                JSONArray drops = modObj.getJSONArray("drops");
                for(int j =0; j < drops.length(); j++)
                {
                    JSONObject dropobj = drops.getJSONObject(j);
                    String location = dropobj.getString("location");
                    double chance = dropobj.getDouble("chance");
                    String rarity2 = dropobj.getString("rarity");
                    String type = dropobj.getString("type");
                    Drop drop = new Drop(chance, rarity2, location, type);
                    dropList.add(drop);



                }
                modData = new ModData(name, rarity, thumbnail, polarity, type2);
            }

        }catch (Exception e)
        {
            e.printStackTrace();

        }
        return modData;

    }
    public List<Drop> retrieveModDrops(String modName) throws Exception {
        String url = "https://api.warframestat.us//mods/search/" + modName.replaceAll("\\s+", "%20") + "/";
        System.out.println(url);
        JSONArray modJsonArray = null;

        List<Drop> dropList = new ArrayList<>();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONArray modJson = new JSONArray(responseBody);
            for (int i = 0; i < modJson.length(); i++) {
                JSONObject modObj = modJson.getJSONObject(i);

                JSONArray drops = modObj.getJSONArray("drops");
                for (int j = 0; j < drops.length(); j++) {
                    JSONObject dropobj = drops.getJSONObject(j);
                    String location = dropobj.getString("location");
                    double chance = dropobj.getDouble("chance");
                    String rarity2 = dropobj.getString("rarity");
                    String type = dropobj.getString("type");
                    Drop drop = new Drop(chance, rarity2, location, type);
                    dropList.add(drop);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dropList;
    }

}
