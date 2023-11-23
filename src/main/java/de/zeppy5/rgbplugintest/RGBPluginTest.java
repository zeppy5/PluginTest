package de.zeppy5.rgbplugintest;

import de.zeppy5.rgbplugintest.commands.InfoCommand;
import de.zeppy5.rgbplugintest.listeners.AuthListener;
import de.zeppy5.rgbplugintest.util.HttpConnection;
import de.zeppy5.rgbplugintest.util.Role;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public final class RGBPluginTest extends JavaPlugin {

    private static RGBPluginTest instance;

    private static String uri;

    private static HashMap<String, Role> roleMap;

    private static HashMap<String, ServerPlayer> playerMap;
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

        setRoleMap(uri + "/roles");

        Objects.requireNonNull(getCommand("info")).setExecutor(new InfoCommand());

        Bukkit.getPluginManager().registerEvents(new AuthListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<String, Role> getRoleMap() {
        return roleMap;
    }

    public static void setRoleMap(String uri) {
        List<Role> roleList = HttpConnection.getRoles(uri);

        for (Role role : roleList) {
            String name = role.getName();
            roleMap.put(name, role);
        }
    }

    public static ServerPlayer getPlayer(String uuid) {
        if (!playerMap.containsKey(uuid)) {
            ServerPlayer player = HttpConnection.getServerPlayer(uri + "/player?uuid=" + uuid);
            playerMap.put(player.getUuid(), player);
        }
        return playerMap.get(uuid);
    }

    public static RGBPluginTest getInstance() {
        return instance;
    }

    public static String getUri() {
        return uri;
    }
}
