package me.kisr.commands;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

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
        add command
         */

        OptionData addUser = new OptionData(OptionType.USER, "user", "Person to add to the ticket.", true);

        commands.add(Commands.slash("add", "Add someone to a ticket.").addOptions(addUser));

        /*
        remove command
         */

        OptionData removeUser = new OptionData(OptionType.USER, "user", "Person to remove to the ticket.", true);

        commands.add(Commands.slash("remove", "Remove someone to a ticket.").addOptions(removeUser));

        /*
        update guild commands
         */

        event.getGuild().updateCommands().addCommands(commands).queue();
    }
}
