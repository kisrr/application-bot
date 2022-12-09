package me.kisr.commands;

import me.kisr.Main;
import me.kisr.utils.FileUtils;
import me.kisr.utils.RoleUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Commands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();

        /*
        sendapplication command
         */

        if (command.equals("sendapplication")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("ASC Application");
                embed.setColor(Color.RED);
                embed.setDescription("Click the button below to create an application ticket!");
                List<net.dv8tion.jda.api.interactions.components.buttons.Button> buttons = new ArrayList<>();
                buttons.add(Button.primary("open-application", "📩"));
                event.getChannel().sendMessageEmbeds(embed.build()).addActionRow(buttons).queue();
                event.reply("The application ticket embed has been sent!").setEphemeral(true).queue();
            } else {
                event.reply("No permission!").setEphemeral(true).queue();
            }
        }

        /*
        add command
         */

        else if (command.equals("add")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || RoleUtils.hasTester(event.getMember())) {
                if (event.getMember().equals(event.getOption("user").getAsMember())) {
                    event.reply("You can't add yourself to the ticket!").queue();
                } else {
                    if (event.getOption("user").getAsMember().getUser().isBot()) {
                        event.reply("You can't add a bot to a ticket!").setEphemeral(true).queue();
                    } else {
                        if (event.getGuild().getCategoryById(Main.config.get("APPLICATION_CATEGORY")).getChannels().contains(event.getChannel())) {
                            event.getChannel().asTextChannel().getManager().putPermissionOverride(event.getOption("user").getAsMember(), EnumSet.of(Permission.VIEW_CHANNEL), null).queue();
                            event.reply("Added " + event.getOption("user").getAsMember().getAsMention() + " to the ticket!").queue();
                        } else {
                            event.reply("You have to be in a ticket to use this command!").setEphemeral(true).queue();
                        }
                    }
                }
            } else {
                event.reply("No permission!").setEphemeral(true).queue();
            }
        }

        /*
        remove command
         */

        else if (command.equals("remove")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || RoleUtils.hasTester(event.getMember())) {
                String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");
                if (event.getGuild().getMemberById(datasplit[0]).equals(event.getOption("user").getAsMember())) {
                    event.reply("You can't remove the owner of the ticket!").setEphemeral(true).queue();
                } else {
                    if (event.getMember().equals(event.getOption("user"))) {
                        event.reply("You can't remove yourself from the ticket!");
                    } else {
                        if (event.getOption("user").getAsMember().getUser().isBot()) {
                            event.reply("You can't remove a bot from a ticket!").setEphemeral(true).queue();
                        } else {
                            if (event.getGuild().getCategoryById(Main.config.get("APPLICATION_CATEGORY")).getChannels().contains(event.getChannel())) {
                                event.getChannel().asTextChannel().getManager().putPermissionOverride(event.getOption("user").getAsMember(), null, EnumSet.of(Permission.VIEW_CHANNEL)).queue();
                                event.reply("Removed " + event.getOption("user").getAsMember().getAsMention() + " to the ticket!").queue();
                            } else {
                                event.reply("You have to be in a ticket to use this command!").setEphemeral(true).queue();
                            }
                        }
                    }
                }
            }

        } else {
            event.reply("No permission!").setEphemeral(true).queue();
        }
    }
}