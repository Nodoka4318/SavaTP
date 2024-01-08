package net.kankantari.savatp.commands

import net.kankantari.savatp.SavaTP
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SavaTPCommand : Command("savatp") {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (args.isEmpty()) {
                SavaTP.sendHelp(sender);
                return true
            }

            val newArgs = args.sliceArray(1..args.size - 1)

            when (args[0]) {
                "set" -> SavaSetCommand().execute(sender, commandLabel, newArgs)
                "delete" -> SavaDeleteCommand().execute(sender, commandLabel, newArgs)
                "list" -> SavaPointsCommand().execute(sender, commandLabel, newArgs)
                else -> SavaTeleportCommand().execute(sender, commandLabel, newArgs)
            }

            return true
        } catch (e: Exception) {
            SavaTP.sendHelp(sender);

            e.printStackTrace();
            return false;
        }
    }
}