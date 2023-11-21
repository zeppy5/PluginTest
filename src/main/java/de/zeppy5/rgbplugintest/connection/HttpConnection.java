package de.zeppy5.rgbplugintest.connection;

import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class HttpConnection {

    public static String getAPI(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).build();
        CompletableFuture<HttpResponse<String>> responseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response;

        try {
            response = responseCompletableFuture.get();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "API CONNECTION ERROR: " + e.getMessage());
            throw new RuntimeException();
        }

        return response.body();
    }

    public static JSONArray getJSONArray(String uri) {
        return new JSONArray(getAPI(uri));
    }

    public static JSONObject getJSONObject(String uri) {
        return new JSONObject(getAPI(uri));
    }

}
