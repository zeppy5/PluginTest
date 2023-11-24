package de.zeppy5.rgbplugintest.playerlist;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerListListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getScoreboard().equals(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        RGBPluginTest.getInstance().getPlayerListManager().setPlayerList(player);
        RGBPluginTest.getInstance().getPlayerListManager().setAllTeams();
    }

}
