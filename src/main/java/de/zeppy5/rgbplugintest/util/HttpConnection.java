package de.zeppy5.rgbplugintest.util;

import com.google.gson.Gson;
import org.bukkit.Bukkit;

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

    public static ServerPlayer getServerPlayer(String uri) {
        return new Gson().fromJson(getAPI(uri), ServerPlayer.class);
    }

}
