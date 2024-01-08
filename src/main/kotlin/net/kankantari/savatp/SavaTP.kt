package net.kankantari.savatp

import net.kankantari.savatp.commands.*
import net.kankantari.savatp.io.Config
import net.kankantari.savatp.io.LocationDataSet
import net.kankantari.savatp.web.ApiServer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Field
import java.util.logging.Logger


class SavaTP : JavaPlugin() {
    override fun onEnable() {
        savaLogger = logger;

        logger.info("Registering commands...");
        registerCommands();

        logger.info("Loading all teleport points...");
        locationDataSet = LocationDataSet.load();
        logger.info("Loaded " + locationDataSet.Locations.size + " teleport points!");

        logger.info("Loading config...");
        savaConfig = Config.load();

        logger.info("Starting HTTP service...");
        apiServer = ApiServer();
        apiServer.start(savaConfig.port);
    }

    override fun onDisable() {
        logger.info("Saving all teleport points...");
        locationDataSet.save();
        logger.info("Saved " + locationDataSet.Locations.size + " teleport points!");
    }

    private fun registerCommands() {
        var f: Field;
        f = SimplePluginManager::class.java.getDeclaredField("commandMap");
        f.setAccessible(true);
        commandMap = f.get(this.server.getPluginManager()) as SimpleCommandMap;

        commands.forEach { command: Command ->
            commandMap.register(command.name, command);
        }
    }

    companion object {
        lateinit var locationDataSet: LocationDataSet;
        lateinit var commandMap: SimpleCommandMap;
        lateinit var savaConfig: Config;
        lateinit var savaLogger: Logger;
        lateinit var apiServer: ApiServer;

        val commands: List<Command>
            get() = listOf(
                SavaTPCommand(),
                SavaSetCommand(),
                SavaDeleteCommand(),
                SavaPointsCommand(),
                SavaTeleportCommand()
            )

        fun sendMessage(entity: CommandSender, message: String) {
            entity.sendMessage("§2§l[§f§lSavaTP§2§l] §f$message");
        }

        fun sendHelp(entity: CommandSender) {
            sendMessage(entity, "SavaTP Help:");
            sendMessage(entity, "   /savatp set <name> - Set a teleport point");
            sendMessage(entity, "   /savatp delete <name> - Delete a teleport point");
            sendMessage(entity, "   /savatp <name> - Teleport to a teleport point");
            sendMessage(entity, "   /savatp list - List all teleport points");
        }

        fun log(message: String) {
            savaLogger.info(message);
        }
    }
}