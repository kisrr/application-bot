package me.kisr.listeners;

import me.kisr.Main;
import me.kisr.utils.FileUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        String modal = event.getModalId();

        /*
        open-application modal
         */

        if (modal.equals("open-application")) {
            if (event.getValue("gamemode").getAsString().equalsIgnoreCase("Crystal") || event.getValue("gamemode").getAsString().equalsIgnoreCase("Sword")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("ASC Application");
                embed.setColor(Color.RED);
                embed.addField("Username", event.getValue("username").getAsString(), false);
                embed.addField("Country", event.getValue("country").getAsString(), false);
                if (event.getValue("gamemode").getAsString().equalsIgnoreCase("Crystal")) {
                    embed.addField("Gamemode", "Crystal", false);
                } else if (event.getValue("gamemode").getAsString().equalsIgnoreCase("Sword")) {
                    embed.addField("Gamemode", "Sword", false);
                }
                embed.addField("How can you help ASC?", event.getValue("help").getAsString(), false);
                if (!event.getValue("clips").getAsString().isEmpty()) {
                    embed.addField("Clips", event.getValue("clips").getAsString(), false);
                } else {
                    embed.addField("Clips", "None", false);
                }

                List<Button> rows = new ArrayList<>();
                rows.add(Button.success("accept-application", "Accept ✅"));
                rows.add(Button.danger("deny-application", "Deny ❌"));
                rows.add(Button.primary("close-application", "Close 🔒"));

                event.reply("Your application ticket has been created!").setEphemeral(true).queue();

                event.getGuild().createTextChannel("t-" + event.getUser().getName(), event.getGuild().getCategoryById(Main.config.get("APPLICATION_CATEGORY")))
                        .addPermissionOverride(event.getGuild().getRoleById(Main.config.get("TESTER_ROLE")), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(event.getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                        .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .complete().sendMessageEmbeds(embed.build()).setContent(event.getMember().getAsMention()).setActionRow(rows).queue(channel -> {
                            File ticketsFile = new File("files/tickets/" + channel.getChannel().getId());
                            File ownersFile = new File("files/owners/" + event.getMember().getId());

                            try {
                                ticketsFile.createNewFile();
                                FileWriter writer = new FileWriter(ticketsFile);
                                writer.write(event.getMember().getId() +
                                        "36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47" +
                                        event.getValue("username").getAsString() +
                                        "36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47" +
                                        event.getValue("gamemode").getAsString());
                                writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                ownersFile.createNewFile();
                                FileWriter writer = new FileWriter(ownersFile);
                                writer.write(channel.getChannel().getId());
                                writer.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

            } else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Invalid gamemode!");
                embed.setColor(Color.RED);
                embed.setDescription("Please enter a valid gamemode (Crystal or Sword)");

                List<Button> buttons = new ArrayList<>();
                buttons.add(Button.primary("open-application", "Refill application"));

                event.replyEmbeds(embed.build()).addActionRow(buttons).setEphemeral(true).queue();
            }
        }

        /*
        accept-application modal
         */

        else if (modal.equals("accept-application")) {
            String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");
            File ownersFile = new File("files/owners/" + datasplit[0]);
            File ticketsFile = new File("files/tickets/" + event.getChannel().getId());

            if (datasplit[2].equalsIgnoreCase("crystal")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                if (event.getGuild().getMemberById(datasplit[0]).getUser().getAvatarUrl() != null) {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getAvatarUrl());
                } else {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getUser().getDefaultAvatarUrl());
                }
                embed.setAuthor("Results");
                embed.addField("Tester", event.getMember().getAsMention(), false);
                embed.addField("Username", datasplit[1], false);
                embed.addField("Gamemode", "Crystal", false);
                embed.addField("Flat Score", event.getValue("flat-score").getAsString(), false);
                embed.addField("Hole Score", event.getValue("hole-score").getAsString(), false);
                embed.addField("Tier", event.getValue("tier").getAsString().toUpperCase(), false);
                if (!event.getValue("notes").getAsString().isEmpty()) {
                    embed.addField("Notes", event.getValue("notes").getAsString(), false);
                } else {
                    embed.addField("Notes", "None", false);
                }
                event.getGuild().getTextChannelById(Main.config.get("RESULTS_CHANNEL")).sendMessageEmbeds(embed.build()).setContent(event.getGuild().getMemberById(datasplit[0]).getAsMention() + " was **accepted**").queue();
                event.getGuild().addRoleToMember(event.getGuild().getMemberById(datasplit[0]), event.getGuild().getRoleById(Main.config.get("ACCEPTED_ROLE"))).queue();
                event.reply("Done sending results!").queue();

                ownersFile.delete();
                ticketsFile.delete();

                event.getChannel().delete().queue();
            } else if (datasplit[2].equalsIgnoreCase("sword")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                if (event.getGuild().getMemberById(datasplit[0]).getUser().getAvatarUrl() != null) {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getAvatarUrl());
                } else {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getUser().getDefaultAvatarUrl());
                }
                embed.setAuthor("Results");
                embed.addField("Tester", event.getMember().getAsMention(), false);
                embed.addField("Username", datasplit[1], false);
                embed.addField("Gamemode", "Sword", false);
                embed.addField("Score", event.getValue("score").getAsString(), false);
                embed.addField("Tier", event.getValue("tier").getAsString().toUpperCase(), false);
                if (!event.getValue("notes").getAsString().isEmpty()) {
                    embed.addField("Notes", event.getValue("notes").getAsString(), false);
                } else {
                    embed.addField("Notes", "None", false);
                }
                event.getGuild().getTextChannelById(Main.config.get("RESULTS_CHANNEL")).sendMessageEmbeds(embed.build()).setContent(event.getGuild().getMemberById(datasplit[0]).getAsMention() + " was **accepted**").queue();
                event.getGuild().addRoleToMember(event.getGuild().getMemberById(datasplit[0]), event.getGuild().getRoleById(Main.config.get("ACCEPTED_ROLE"))).queue();

                ownersFile.delete();
                ticketsFile.delete();

                event.reply("Done sending results!").setEphemeral(true).queue();

                event.getChannel().delete().queue();
            }
        }

        /*
        deny-application modal
         */

        else if (modal.equals("deny-application")) {
            String[] datasplit = FileUtils.readFile(new File("files/tickets/" + event.getChannel().getId())).split("36280b7179c69735773b65d0c595d3f1114400367759a2a21a22870e14a4ae47");
            File ownersFile = new File("files/owners/" + datasplit[0]);
            File ticketsFile = new File("files/tickets/" + event.getChannel().getId());

            if (datasplit[2].equalsIgnoreCase("crystal")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                if (event.getGuild().getMemberById(datasplit[0]).getUser().getAvatarUrl() != null) {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getAvatarUrl());
                } else {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getUser().getDefaultAvatarUrl());
                }
                embed.setAuthor("Results");
                embed.addField("Tester", event.getMember().getAsMention(), false);
                embed.addField("Username", datasplit[1], false);
                embed.addField("Gamemode", "Crystal", false);
                embed.addField("Flat Score", event.getValue("flat-score").getAsString(), false);
                embed.addField("Hole Score", event.getValue("hole-score").getAsString(), false);
                embed.addField("Tier", event.getValue("tier").getAsString().toUpperCase(), false);
                if (!event.getValue("notes").getAsString().isEmpty()) {
                    embed.addField("Notes", event.getValue("notes").getAsString(), false);
                } else {
                    embed.addField("Notes", "None", false);
                }
                event.getGuild().getTextChannelById(Main.config.get("RESULTS_CHANNEL")).sendMessageEmbeds(embed.build()).setContent(event.getGuild().getMemberById(datasplit[0]).getAsMention() + " was **denied**").queue();
                event.reply("Done sending results!").queue();

                ownersFile.delete();
                ticketsFile.delete();

                event.getChannel().delete().queue();
            } else if (datasplit[2].equalsIgnoreCase("sword")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.RED);
                if (event.getGuild().getMemberById(datasplit[0]).getUser().getAvatarUrl() != null) {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getAvatarUrl());
                } else {
                    embed.setThumbnail(event.getGuild().getMemberById(datasplit[0]).getUser().getDefaultAvatarUrl());
                }
                embed.setAuthor("Results");
                embed.addField("Tester", event.getMember().getAsMention(), false);
                embed.addField("Username", datasplit[1], false);
                embed.addField("Gamemode", "Sword", false);
                embed.addField("Score", event.getValue("score").getAsString(), false);
                embed.addField("Tier", event.getValue("tier").getAsString().toUpperCase(), false);
                if (!event.getValue("notes").getAsString().isEmpty()) {
                    embed.addField("Notes", event.getValue("notes").getAsString(), false);
                } else {
                    embed.addField("Notes", "None", false);
                }
                event.getGuild().getTextChannelById(Main.config.get("RESULTS_CHANNEL")).sendMessageEmbeds(embed.build()).setContent(event.getGuild().getMemberById(datasplit[0]).getAsMention() + " was **denied**").queue();

                ownersFile.delete();
                ticketsFile.delete();

                event.reply("Done sending results!").setEphemeral(true).queue();

                event.getChannel().delete().queue();
            }
        }
    }
}
