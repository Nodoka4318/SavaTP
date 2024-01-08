package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import net.kankantari.savatp.io.ApiKeyData
import net.kankantari.savatp.io.ApiKeyDataSet
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SavaRegisterCommand : Command("savaregister") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            SavaTP.sendMessage(sender, "You must be a player to use this command");
            return true;
        }

        var apikeys = SavaTP.apiKeyDataSet.apiKeyDatas;
        var apikey = apikeys.find { it.playerId == sender.uniqueId.toString() };

        if (apikey == null) {
            var newKey = ApiKeyData();
            newKey.playerId = sender.uniqueId.toString();
            newKey.apiKey = ApiKeyDataSet.generateApiKey();

            SavaTP.sendMessage(sender, "Your API key is: §6§l${newKey.apiKey}");
            return true;
        } else  {
            SavaTP.sendMessage(sender, "You already have an API key: §6§l${apikey.apiKey}");
            return true;
        }
    }
}