package net.dirtcraft.julian.actionbarcoords;

import net.luckperms.api.*;
import net.luckperms.api.node.*;
import net.luckperms.api.model.user.*;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

public class Menu implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        Player player = (Player) source;

        if(player.hasPermission("actionbarcoords.enabled")) {

            enablePagination(player);

        } else if(!player.hasPermission("actionbarcoords.enabled")) {

            disabledPagination(player);

        }


        return CommandResult.success();
    }

    private void enablePagination(Player player) {
        String togglePadding = ConfigManager.getConfNode("Pagination", "padding").getString();
        Text msgtogglePadding = TextSerializers.FORMATTING_CODE.deserialize(togglePadding);
        String toggleTitle = ConfigManager.getConfNode("Toggle-Menu", "title").getString();
        String toggleContents = ConfigManager.getConfNode("Toggle-Menu", "message").getString();
        String toggleHover = ConfigManager.getConfNode("Toggle-Menu", "hover").getString();
        String lpserverContext = ConfigManager.getConfNode("LuckPerms", "server-context").getString();


        PaginationList.builder()
                .title(TextSerializers.FORMATTING_CODE.deserialize(toggleTitle
                        .replace("{toggle}", ConfigManager.getConfNode("Toggle-Menu", "enabled").getString())))
                .padding(msgtogglePadding)
                .contents(

                        Text.builder()
                                .append(Text.builder()
                                        .append(TextSerializers.FORMATTING_CODE.deserialize(
                                                toggleContents.replace("{toggle}",
                                                        ConfigManager.getConfNode("Toggle-Menu", "enabled").getString())
                                                        .replace("{playername}", player.getName())))
                                        .build())
                                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize(toggleHover.replace("{toggle}",
                                        ConfigManager.getConfNode("Toggle-Menu", "enabled").getString()))))
                                .onClick(TextActions.executeCallback(disable -> {

                                    User user =  LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
                                    Node permission = Node.builder("actionbarcoords.enabled")
                                            .value(false)
                                            .build();

                                    user.data().add(permission);
                                     LuckPermsProvider.get().getUserManager().saveUser(user);

                                    disabledPagination(player);


                                }))
                                .build()

                )
                .build()
                .sendTo(player);


    }

    private void disabledPagination(Player player) {
        String togglePadding = ConfigManager.getConfNode("Pagination", "padding").getString();
        Text msgtogglePadding = TextSerializers.FORMATTING_CODE.deserialize(togglePadding);
        String toggleTitle = ConfigManager.getConfNode("Toggle-Menu", "title").getString();
        String toggleContents = ConfigManager.getConfNode("Toggle-Menu", "message").getString();
        String toggleHover = ConfigManager.getConfNode("Toggle-Menu", "hover").getString();
        String lpserverContext = ConfigManager.getConfNode("LuckPerms", "server-context").getString();

        PaginationList.builder()
                .title(TextSerializers.FORMATTING_CODE.deserialize(toggleTitle
                        .replace("{toggle}", ConfigManager.getConfNode("Toggle-Menu", "disabled").getString())))
                .padding(msgtogglePadding)
                .contents(

                        Text.builder()
                                .append(Text.builder()
                                        .append(TextSerializers.FORMATTING_CODE.deserialize(
                                                toggleContents.replace("{toggle}",
                                                        ConfigManager.getConfNode("Toggle-Menu", "disabled").getString())
                                                        .replace("{playername}", player.getName())))
                                        .build())
                                .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize(toggleHover.replace("{toggle}",
                                        ConfigManager.getConfNode("Toggle-Menu", "disabled").getString()))))
                                .onClick(TextActions.executeCallback(enable->{

                                    User user =  LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
                                    Node permission = Node.builder("actionbarcoords.enabled")
                                            .value(true)
                                            .build();

                                    user.data().add(permission);
                                    LuckPermsProvider.get().getUserManager().saveUser(user);

                                    enablePagination(player);

                                }))
                                .build()

                )
                .build()
                .sendTo(player);

    }

}
