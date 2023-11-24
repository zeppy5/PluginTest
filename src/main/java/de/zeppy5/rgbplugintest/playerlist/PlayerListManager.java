package de.zeppy5.rgbplugintest.playerlist;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.util.Role;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class PlayerListManager {

    public void setPlayerList(Player player) {
        player.setPlayerListHeaderFooter(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(RGBPluginTest.getInstance().getConfig().getString("tablistheader"))),
                ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(RGBPluginTest.getInstance().getConfig().getString("tablistfooter"))));
    }

    public void setAllTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        for (Player p : Bukkit.getOnlinePlayers()) {
            ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(p.getUniqueId()));
            Role role = RGBPluginTest.getRoleHighestPriority(RGBPluginTest.getPlayerRoles(serverPlayer));

            if (role == null) {
                continue;
            }

            Team team = scoreboard.getTeam(role.getPriority() + role.getName());

            if (team == null) {
                team = scoreboard.registerNewTeam(role.getPriority() + role.getName());
            }

            team.setPrefix(ChatColor.translateAlternateColorCodes('&', role.getPrefix() + " "));

            team.addEntry(p.getName());
        }

    }

}
