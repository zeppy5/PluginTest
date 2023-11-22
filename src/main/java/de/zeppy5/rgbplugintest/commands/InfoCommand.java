package de.zeppy5.rgbplugintest.commands;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.util.HttpConnection;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("Use: /info [Player]");
            return true;
        }

        ServerPlayer player = HttpConnection.getServerPlayer(RGBPluginTest.getUri()
                + "/player?"
                + Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());

        sender.sendMessage("uuid: " + player.getUuid() + " auth: " + player.getAuth(), " roles: " + player.getRoles());

        return false;
    }
}
