package de.zeppy5.rgbplugintest.commands;

import de.zeppy5.rgbplugintest.RGBPluginTest;
import de.zeppy5.rgbplugintest.connection.HttpConnection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        JSONArray array = HttpConnection.getJSONArray(RGBPluginTest.getUri());
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            Iterator<String> keys = o.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                sender.sendMessage("KEY: " + key + "   VALUE: " + o.get(key));
            }
        }

        return false;
    }
}
