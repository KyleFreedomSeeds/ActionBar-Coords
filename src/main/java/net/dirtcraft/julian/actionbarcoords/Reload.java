package net.dirtcraft.julian.actionbarcoords;

import com.google.inject.Inject;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.slf4j.Logger;

public class Reload implements CommandExecutor {

    @Inject
    private Logger logger;

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&aReloading ActionBar Coords..."));
	logger.info("Reloading ActionBar Coords...");
        ConfigManager.load();
        src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&aActionBar Coords was reloaded successfully!"));
        logger.info("ActionBar Coords was reloaded successfully!");
        return CommandResult.success();
    }

}
