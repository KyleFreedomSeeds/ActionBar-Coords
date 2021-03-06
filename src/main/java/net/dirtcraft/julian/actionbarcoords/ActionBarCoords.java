package net.dirtcraft.julian.actionbarcoords;

import com.google.inject.Inject;
import net.luckperms.api.*;
import net.luckperms.api.node.*;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.platform.PluginMetadata;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.nio.file.Path;

@Plugin(
        id = "actionbar-coords",
        name = "ActionBar Coords",
        description = "Neat plugin that shows toggleable coords on your action bar.",
        authors = {"juliann", "Ported LP By KyleCG"},
		dependencies = @Dependency(id = "luckperms", optional = false)
)
public class ActionBarCoords {

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path dir;

    private ActionBarCoords instance;
    private CommandSource source;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        instance = this;
        ConfigManager.setup(dir);
        ConfigManager.load();

        CommandSpec reloadCmd = CommandSpec.builder()
                .description(Text.of("Reload command for " + container.getName()))
                .executor(new Reload())
                .permission("actionbarcoords.reload")
                .build();
        CommandSpec discordCmd = CommandSpec.builder()
                .description(Text.of("Discord link for " + container.getName()))
                .executor(new Discord())
                .build();
        CommandSpec command = CommandSpec.builder()
                .description(Text.of("Base command for " + container.getName()))
                .executor(new Menu())
                .permission("actionbarcoords.base")
                .child(reloadCmd, "reload", "rl")
                .child(discordCmd, "discord", "disc")
                .build();

        Sponge.getCommandManager().register(this, command, "coords", "actionbarcoords", "pos", "xyz");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        String lpserverContext = ConfigManager.getConfNode("LuckPerms", "server-context").getString();

        logger.info(container.getName()
                + " running (version "
                + container.getVersion().orElse("")
                + ")! Hey, I'm alive!");

        if (ConfigManager.getConfNode("Action-Bar", "enabled-by-default").getBoolean()) {
            Group group = LuckPermsProvider.get().getGroupManager().getGroup("default");
            Node permission = Node.builder("actionbarcoords.enabled")
                    .value(true)
                    .build();

            group.data().add(permission);
            LuckPermsProvider.get().getGroupManager().saveGroup(group);
        } else if (!ConfigManager.getConfNode("Action-Bar", "enabled-by-default").getBoolean()) {

            Group group = LuckPermsProvider.get().getGroupManager().getGroup("default");
            Node permission = Node.builder("actionbarcoords.enabled")
                    .value(false)
                    .build();

            group.data().add(permission);
            LuckPermsProvider.get().getGroupManager().saveGroup(group);

        }

    }

    @Listener
    public void onMoveEvent(MoveEntityEvent event) {
        String actionBar = ConfigManager.getConfNode("Action-Bar", "message").getString();
        if (event.getCause().root() instanceof Player) {
            Player player = (Player) event.getCause().root();
            String x = String.valueOf(player.getPosition().getFloorX());
            String y = String.valueOf(player.getPosition().getFloorY());
            String z = String.valueOf(player.getPosition().getFloorZ());

            if (player.hasPermission("actionbarcoords.enabled")) {
                player.sendMessage(ChatTypes.ACTION_BAR, TextSerializers.FORMATTING_CODE.deserialize(actionBar.replace("{x}", x).replace("{y}", y).replace("{z}", z)));
            }
        }

    }

    @Listener
    public void onReload(GameReloadEvent event) {
		logger.info("Reloading ActionBar Coords...");
        ConfigManager.load();
        logger.info("ActionBar Coords was reloaded successfully!");
    }


}
