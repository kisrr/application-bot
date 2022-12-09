package me.kisr.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commands = new ArrayList<>();

        /*
        sendapplication command
         */

        commands.add(Commands.slash("sendapplication", "Send the create application embed."));

        /*
        update guild commands
         */

        event.getGuild().updateCommands().addCommands(commands).queue();
    }
}
