package de.zeppy5.rgbplugintest;

import de.zeppy5.rgbplugintest.commands.InfoCommand;
import de.zeppy5.rgbplugintest.listeners.AuthListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;

public final class RGBPluginTest extends JavaPlugin {

    private static RGBPluginTest instance;

    private static String uri;
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        uri = getConfig().getString("uri");

        int code;

        try {
            assert uri != null;
            URL url = new URL(uri + "/player");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            code = connection.getResponseCode();
            if (code != 200) {
                Bukkit.getLogger().log(Level.SEVERE, "API CONNECTION RETURNED CODE: " + code);
            } else {
                Bukkit.getLogger().log(Level.INFO, "CONNECTED TO API SUCCESSFULLY: " + code);
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "API CONNECTION ERROR: " + e.getMessage());
        }


        Objects.requireNonNull(getCommand("info")).setExecutor(new InfoCommand());

        Bukkit.getPluginManager().registerEvents(new AuthListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RGBPluginTest getInstance() {
        return instance;
    }

    public static String getUri() {
        return uri;
    }
}
