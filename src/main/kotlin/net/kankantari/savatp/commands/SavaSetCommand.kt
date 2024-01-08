package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import net.kankantari.savatp.io.LocationData
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SavaSetCommand : Command("setpoint") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            SavaTP.sendMessage(sender, "You must be a player to use this command");
            return true;
        }

        if (args == null || args.isEmpty()) {
            SavaTP.sendHelp(sender);
            return true;
        }

        val name = args[0];

        if (SavaTP.locationDataSet.getLocation(name) != null) {
            SavaTP.sendMessage(sender, "That location already exists! Please try again with a different name.");
            return true;
        }

        val location = sender.location;

        SavaTP.locationDataSet.addLocation(
            LocationData(
                location.x,
                location.y,
                location.z,
                location.yaw,
                location.pitch,
                location.world.name,
                name,
                sender.uniqueId.toString()
            )
        );

        Bukkit.getOnlinePlayers().forEach {
            SavaTP.sendMessage(it, "§6§l${sender.name} §fhas set a new location: §6§l$name");
        }

        return true;
    }
}