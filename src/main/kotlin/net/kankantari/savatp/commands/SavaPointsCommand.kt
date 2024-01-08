package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SavaPointsCommand : Command("savapoints") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        val locations = SavaTP.locationDataSet.Locations;
        if (locations.isEmpty()) {
            SavaTP.sendMessage(sender, "There are no teleport points!");
            return true;
        }

        SavaTP.sendMessage(sender, "Teleport points:");
        locations.forEach {
            SavaTP.sendMessage(sender, "   §6§l${it.Name} §f(${it.X}, ${it.Y}, ${it.Z}, ${it.World})", false);
        }

        return true;
    }
}