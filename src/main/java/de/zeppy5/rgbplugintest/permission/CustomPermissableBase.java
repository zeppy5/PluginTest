package de.zeppy5.rgbplugintest.permission;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;

import java.util.Arrays;
import java.util.List;

public class CustomPermissableBase extends PermissibleBase {

    private Player player;
    public CustomPermissableBase(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean hasPermission(String inName) {
        ServerPlayer serverPlayer = RGBPluginTest.getPlayer(String.valueOf(player.getUniqueId()));
        List<String> permissions = RGBPluginTest.getPlayerPermissions(serverPlayer);

        if(Arrays.asList("bukkit.broadcast.user", "bukkit.broadcast", "bukkit.command.version").contains(inName)) {
            return true;
        }

        if(permissions.contains("-" + inName)) {
            return false;
        }

        if(permissions.contains("*") || player.isOp()) {
            return true;
        }

        return permissions.contains(inName);
    }
}
