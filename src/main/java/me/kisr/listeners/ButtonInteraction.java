package me.kisr.listeners;

import me.kisr.Main;
import me.kisr.utils.FileUtils;
import me.kisr.utils.RoleUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ButtonInteraction extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String button = event.getButton().getId();

        /*
        open-application button
         */

        if (button.equals("open-application")) {
            if (Files.notExists(Path.of("files/owners/" + event.getMember().getId()))) {

                List<ActionRow> rows = new ArrayList<>();

                TextInput username = TextInput.create("username", "WHAT IS YOUR MC USERNAME?", TextInputStyle.SHORT)
                        .setRequired(true)
                        .setPlaceholder("Username")
                        .build();

                TextInput country = TextInput.create("country", "WHAT COUNTRY DO YOU LIVE IN?", TextInputStyle.SHORT)
                        .setRequired(true)
                        .setPlaceholder("Country")
                        .build();

                TextInput gamemode = TextInput.create("gamemode", "WHAT GAMEMODE DO YOU MAIN?", TextInputStyle.SHORT)
                        .setRequired(true)
                        .setPlaceholder("Crystal or Sword")
                        .build();

                TextInput help = TextInput.create("help", "HOW CAN YOU HELP ASC?", TextInputStyle.PARAGRAPH)
                        .setRequired(true)
                        .setPlaceholder("How can you help us")
                        .build();

                TextInput clips = TextInput.create("clips", "CLIPS", TextInputStyle.PARAGRAPH)
                        .setRequired(false)
                        .setPlaceholder("(Leave empty if you have none)")
                        .build();

                rows.add(ActionRow.of(username));
                rows.add(ActionRow.of(country));
                rows.add(ActionRow.of(gamemode));
                rows.add(ActionRow.of(help));
                rows.add(ActionRow.of(clips));

                Modal modal = Modal.create("open-application", "ASC Application")
                        .addActionRows(rows)
                        .build();

                event.replyModal(modal).queue();

            } else if (Files.exists(Path.of("files/owners/" + event.getMember().getId()))) {
                event.reply("You already have an application ticket opened! " + event.getGuild().getTextChannelById(FileUtils.readFile(new File("files/owners/" + event.getMember().getId()))).getAsMention()).setEphemeral(true).queue();
            }
        }

        /*
        accept-application button
         */

        else if (button.equals("accept-application")) {
            String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");

            if (event.getMember().hasPermission(EnumSet.of(Permission.ADMINISTRATOR)) || RoleUtils.hasTester(event.getMember())) {
                if (datasplit[2].equalsIgnoreCase("crystal")) {
                    TextInput flatscore = TextInput.create("flat-score", "FLAT SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 2-1")
                            .build();

                    TextInput holescore = TextInput.create("hole-score", "HOLE SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 1-2")
                            .build();

                    TextInput tier = TextInput.create("tier", "TIER", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. HT4")
                            .build();

                    TextInput notes = TextInput.create("notes", "NOTES", TextInputStyle.PARAGRAPH)
                            .setRequired(false)
                            .setPlaceholder("(Optional leave empty if none)")
                            .build();

                    List<ActionRow> rows = new ArrayList<>();
                    rows.add(ActionRow.of(flatscore));
                    rows.add(ActionRow.of(holescore));
                    rows.add(ActionRow.of(tier));
                    rows.add(ActionRow.of(notes));

                    Modal modal = Modal.create("accept-application", "Accept")
                            .addActionRows(rows)
                            .build();

                    event.replyModal(modal).queue();
                } else if (datasplit[2].equalsIgnoreCase("sword")) {
                    TextInput score = TextInput.create("score", "SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 3-2")
                            .build();

                    TextInput tier = TextInput.create("tier", "TIER", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. HT4")
                            .build();

                    TextInput notes = TextInput.create("notes", "NOTES", TextInputStyle.PARAGRAPH)
                            .setRequired(false)
                            .setPlaceholder("(Leave empty if you have none)")
                            .build();

                    List<ActionRow> rows = new ArrayList<>();
                    rows.add(ActionRow.of(score));
                    rows.add(ActionRow.of(tier));
                    rows.add(ActionRow.of(notes));

                    Modal modal = Modal.create("accept-application", "Accept")
                            .addActionRows(rows)
                            .build();

                    event.replyModal(modal).queue();
                }
            } else {
                event.reply("No permission!").setEphemeral(true).queue();
            }
        }

        /*
        deny-application button
         */

        else if (button.equals("deny-application")) {
            String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");

            if (event.getMember().hasPermission(EnumSet.of(Permission.ADMINISTRATOR)) || RoleUtils.hasTester(event.getMember())) {
                if (datasplit[2].equalsIgnoreCase("crystal")) {
                    TextInput flatscore = TextInput.create("flat-score", "FLAT SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 2-1")
                            .build();

                    TextInput holescore = TextInput.create("hole-score", "HOLE SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 1-2")
                            .build();

                    TextInput tier = TextInput.create("tier", "TIER", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. HT4")
                            .build();

                    TextInput notes = TextInput.create("notes", "NOTES", TextInputStyle.PARAGRAPH)
                            .setRequired(false)
                            .setPlaceholder("(Optional leave empty if none)")
                            .build();

                    List<ActionRow> rows = new ArrayList<>();
                    rows.add(ActionRow.of(flatscore));
                    rows.add(ActionRow.of(holescore));
                    rows.add(ActionRow.of(tier));
                    rows.add(ActionRow.of(notes));

                    Modal modal = Modal.create("deny-application", "Deny")
                            .addActionRows(rows)
                            .build();

                    event.replyModal(modal).queue();
                } else if (datasplit[2].equalsIgnoreCase("sword")) {
                    TextInput score = TextInput.create("score", "SCORE", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. 3-2")
                            .build();

                    TextInput tier = TextInput.create("tier", "TIER", TextInputStyle.SHORT)
                            .setRequired(true)
                            .setPlaceholder("eg. HT4")
                            .build();

                    TextInput notes = TextInput.create("notes", "NOTES", TextInputStyle.PARAGRAPH)
                            .setRequired(false)
                            .setPlaceholder("(Leave empty if you have none)")
                            .build();

                    List<ActionRow> rows = new ArrayList<>();
                    rows.add(ActionRow.of(score));
                    rows.add(ActionRow.of(tier));
                    rows.add(ActionRow.of(notes));

                    Modal modal = Modal.create("deny-application", "Deny")
                            .addActionRows(rows)
                            .build();

                    event.replyModal(modal).queue();
                }
            } else {
                event.reply("No permission!").setEphemeral(true).queue();
            }
        }

        /*
        close-application button
         */

        else if (button.equals("close-application")) {
            if (event.getMember().hasPermission(EnumSet.of(Permission.ADMINISTRATOR)) || RoleUtils.hasTester(event.getMember())) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Warning!");
                embed.setColor(Color.RED);
                embed.setDescription("Are you sure you want to close that ticket?");
                List<net.dv8tion.jda.api.interactions.components.buttons.Button> buttons = new ArrayList<>();
                buttons.add(Button.danger("confirm-close", "Confirm ❗"));
                buttons.add(Button.success("cancel-close", "Cancel 🛑"));
                event.replyEmbeds(embed.build()).addActionRow(buttons).queue();
            } else {
                event.reply("No permission!").setEphemeral(true).queue();
            }
        }

        /*
        confirm-close button
         */

        else if (button.equals("confirm-close")) {
            if (event.getMember().hasPermission(EnumSet.of(Permission.ADMINISTRATOR)) || RoleUtils.hasTester(event.getMember())) {
                String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");

                File ticketsFile = new File("files/tickets/" + event.getChannel().getId());
                File ownersFile = new File("files/owners/" + datasplit[0]);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Application Closed");
                embed.setColor(Color.RED);

                embed.addField("Opened by", event.getGuild().getMemberById(datasplit[0]).getAsMention(), true);
                embed.addField("Closed by", event.getUser().getAsMention(), true);

                event.getGuild().getTextChannelById(Main.config.get("LOGS_CHANNEL")).sendMessageEmbeds(embed.build()).queue();

                ownersFile.delete();
                ticketsFile.delete();

                event.reply("Deleting channel now!").queue();

                event.getChannel().delete().queue();
            }
        }

        /*
        cancel-close
         */

        else if (button.equals("close-cancel")) {
            event.getMessage().delete().queue();
            event.reply("Cancelled close request!").setEphemeral(true).queue();
        }
    }
}
