package de.zeppy5.rgbplugintest.commands;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.util.HttpConnection;
import de.zeppy5.rgbplugintest.util.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage("Use: /info [Player]");
            return true;
        }

        ServerPlayer player = HttpConnection.getServerPlayer(String.valueOf(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId()));

        sender.sendMessage("uuid: " + player.getUuid() + " auth: " + player.getAuth(), " roles: " + player.getRoles());

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();

        if (!sender.hasPermission("api.info")) {
            return null;
        }

        if (args.length == 0) {
            return list;
        }

        if (args.length == 1) {
            Bukkit.getOnlinePlayers().forEach(player -> list.add(player.getDisplayName()));
        }

        List<String> completerList = new ArrayList<>();
        String currentArg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if(s1.startsWith(currentArg)) {
                completerList.add(s);
            }
        }

        return completerList;
    }
}
