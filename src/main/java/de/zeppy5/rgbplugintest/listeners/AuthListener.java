package de.zeppy5.rgbplugintest.listeners;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class AuthListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));
        if (!serverPlayer.getAuth()) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(
                    RGBPluginTest.getInstance().getConfig().getString("notauthkickmessage"))));
        }
    }

}
