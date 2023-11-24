package de.zeppy5.rgbplugintest.listeners;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.permission.CustomPermissableBase;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftHumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.lang.reflect.Field;
import java.util.Objects;

public class AuthListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));
        if (!serverPlayer.getAuth()) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(
                    RGBPluginTest.getInstance().getConfig().getString("notauthkickmessage"))));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        try {
            Field field = CraftHumanEntity.class.getDeclaredField("perm");
            field.setAccessible(true);
            field.set(player, new CustomPermissableBase(player));
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));
        if (!serverPlayer.getAuth()) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(
                    RGBPluginTest.getInstance().getConfig().getString("notauthkickmessage"))));
        }
        String prefix = RGBPluginTest.getRoleHighestPriority(RGBPluginTest.getPlayerRoles(serverPlayer)).getPrefix();
        String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(RGBPluginTest.getInstance()
                .getConfig().getString("joinmessage"))
                .replace("%player", player.getDisplayName())
                .replace("%prefix", prefix));
        event.setJoinMessage(message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));

        String prefix = RGBPluginTest.getRoleHighestPriority(RGBPluginTest.getPlayerRoles(serverPlayer)).getPrefix();
        String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(RGBPluginTest.getInstance()
                        .getConfig().getString("quitmessage"))
                .replace("%player", player.getDisplayName())
                .replace("%prefix", prefix));
        event.setQuitMessage(message);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));
        String prefix = ChatColor.translateAlternateColorCodes('&'
                , RGBPluginTest.getRoleHighestPriority(RGBPluginTest.getPlayerRoles(serverPlayer)).getPrefix());

        event.setFormat(prefix + ChatColor.RESET + " <%s>: %s");
    }

}
