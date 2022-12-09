package me.kisr.listeners;

import me.kisr.Main;
import me.kisr.utils.FormatUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileWriter;

public class MessageRecieved extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMember().getUser().isBot())
            return;

        if (event.getMessage().getCategory().getId().equals(Main.config.get("APPLICATION_CATEGORY"))) {
            File transcriptFile = new File("files/transcripts/" + event.getChannel().getId() + ".txt");

            try {
                transcriptFile.createNewFile();
                FileWriter writer = new FileWriter(transcriptFile, true);
                writer.append(event.getMember().getUser().getAsTag() + " [" + FormatUtils.getTime() + "]" + "\n" + event.getMessage().getContentRaw() + "\n\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
