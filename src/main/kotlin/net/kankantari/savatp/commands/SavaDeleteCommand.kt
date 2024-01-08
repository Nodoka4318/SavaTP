package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SavaDeleteCommand : Command("delpoint") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args == null || args.isEmpty()) {
            SavaTP.sendHelp(sender);
            return true;
        }

        val name = args[0];
        val location = SavaTP.locationDataSet.getLocation(name);

        if (location == null) {
            SavaTP.sendMessage(sender, "Teleport point not found!");
            return true;
        } else {
            SavaTP.locationDataSet.deleteLocation(name);
            SavaTP.sendMessage(sender, "Teleport point deleted!");

            Bukkit.getOnlinePlayers().forEach {
                SavaTP.sendMessage(it, "Teleport point §6§l$name §fhas been deleted by §6§l${sender.name}§f!");
            }

            return true;
        }
    }
}