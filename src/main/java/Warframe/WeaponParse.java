package Warframe;

import Handlers.ItemData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeaponParse {
    private static final String BASE_URL = "https://api.warframestat.us/weapons/";

    public static ItemData retrieveItemData(String weaponName) {
        String weaponUrl = BASE_URL + weaponName.replaceAll("\\s+", "%20") + "/";
        ItemData itemData = null;

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(weaponUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            JSONObject weaponJson = new JSONObject(responseBody);
            String name = weaponJson.getString("name");
            String description = weaponJson.getString("description");
            String type = weaponJson.getString("type");
            int masteryReq = weaponJson.getInt("masteryReq");
            String damage = weaponJson.getJSONObject("damage").toString();
            double criticalChance = weaponJson.getDouble("criticalChance") * 100;
            double criticalMultiplier = weaponJson.getDouble("criticalMultiplier");
            double procChance = weaponJson.getDouble("procChance") * 100;
            double fireRate = weaponJson.getDouble("fireRate");
            int magazineSize = weaponJson.getInt("magazineSize");
            double reloadTime = weaponJson.getDouble("reloadTime");

            itemData = new ItemData(name, description, type, masteryReq, damage,
                    criticalChance, criticalMultiplier, procChance, fireRate, magazineSize,
                    reloadTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemData;
    }
}
