package de.zeppy5.rgbplugintest.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import de.zeppy5.rgbplugintest.RGBPluginTest;
import org.apache.http.client.utils.URIBuilder;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class HttpConnection {

    public static String getAPI(URI uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
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

    public static ServerPlayer getServerPlayer(String value) {
        try {
            return new Gson().fromJson(getAPI(new URIBuilder(RGBPluginTest.getUri() + "/player").addParameter("uuid", value).build()), ServerPlayer.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Role> getRoles() {
        try {
            Type type =  new TypeToken<ArrayList<Role>>(){}.getType();
            return new Gson().fromJson(getAPI(new URIBuilder(RGBPluginTest.getUri() + "/roles").build()), type);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
