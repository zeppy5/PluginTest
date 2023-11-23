package de.zeppy5.rgbplugintest;

import de.zeppy5.rgbplugintest.commands.InfoCommand;
import de.zeppy5.rgbplugintest.commands.ReloadCommand;
import de.zeppy5.rgbplugintest.listeners.AuthListener;
import de.zeppy5.rgbplugintest.util.HttpConnection;
import de.zeppy5.rgbplugintest.util.Role;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

        roleMap = new HashMap<>();
        playerMap = new HashMap<>();

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

        setRoleMap();

        Objects.requireNonNull(getCommand("info")).setExecutor(new InfoCommand());
        Objects.requireNonNull(getCommand("reloadapi")).setExecutor(new ReloadCommand());

        Bukkit.getPluginManager().registerEvents(new AuthListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<String, Role> getRoleMap() {
        return roleMap;
    }

    public static void setRoleMap() {
        if (!roleMap.isEmpty()) {
            roleMap.clear();
        }

        List<Role> roleList = HttpConnection.getRoles(uri + "/roles");

        for (Role role : roleList) {
            String name = role.getName();
            roleMap.put(name, role);
        }
    }

    public static void setPlayerMap() {
        if (!playerMap.isEmpty()) {
            playerMap.clear();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            ServerPlayer serverPlayer = HttpConnection.getServerPlayer(uri + "/player?uuid=" + player.getUniqueId());
            playerMap.put(serverPlayer.getUuid(), serverPlayer);
        }
    }

    public static ServerPlayer getPlayer(String uuid) {
        if (!playerMap.containsKey(uuid)) {
            ServerPlayer player = HttpConnection.getServerPlayer(uri + "/player?uuid=" + uuid);
            playerMap.put(player.getUuid(), player);
        }
        return playerMap.get(uuid);
    }

    public static List<Role> getPlayerRoles(ServerPlayer player) {
        List<Role> roles = new ArrayList<>();
        for (String role : player.getRoles()) {
            roles.add(roleMap.get(role));
        }
        return roles;
    }

    public static Role getRoleHighestPriority(List<Role> roles) {
        Role highestRole = null;
        for (Role role : roles) {
            if (role != null && (highestRole == null ||  role.getPriority() > highestRole.getPriority())) {
                highestRole = role;
            }
        }
        return highestRole;
    }

    public static RGBPluginTest getInstance() {
        return instance;
    }

    public static String getUri() {
        return uri;
    }
}
