package de.zeppy5.rgbplugintest.commands;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        RGBPluginTest.setRoleMap();
        RGBPluginTest.setPlayerMap();

        sender.sendMessage(ChatColor.GREEN + "Reloaded API info");

        return false;
    }
}
