package net.dirtcraft.julian.actionbarcoords;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.net.MalformedURLException;
import java.net.URL;

public class Discord implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) throws CommandException {
        try {
            PaginationList.builder()
                    .title(TextSerializers.FORMATTING_CODE.deserialize("&aPlugin Support"))
                    .contents(
                            Text.builder()
                                    .append(Text.builder()
                                            .append(TextSerializers.FORMATTING_CODE.deserialize("\n&6Click to join our Plugin Support Discord server\n"))
                                            .build())
                                    .onHover(TextActions.showText(TextSerializers.FORMATTING_CODE.deserialize("&6Click for assistance for our plugins!")))
                                    .onClick(TextActions.openUrl(new URL("https://discord.gg/mGgfyaS")))
                                    .build()
                    )
                    .padding(TextSerializers.FORMATTING_CODE.deserialize("&8&m-"))
            .sendTo(source);


        } catch (MalformedURLException ignored) {}

        return CommandResult.success();
    }

}
