package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SavaTeleportCommand : Command("savateleport") {
    override fun execute(sender: CommandSender, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            SavaTP.sendMessage(sender, "You must be a player to use this command");
            return true;
        }

        if (args == null || args.isEmpty()) {
            SavaTP.sendHelp(sender);
            return true;
        }

        val location = SavaTP.locationDataSet.getLocation(args[0]);

        if (location == null) {
            SavaTP.sendMessage(sender, "That location does not exist");
            return true;
        } else {
            sender.teleport(location.getLocation());
            SavaTP.sendMessage(sender, "Teleported to " + location.Name);

            return true;
        }
    }
}