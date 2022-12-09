package me.kisr;

import io.github.cdimascio.dotenv.Dotenv;
import me.kisr.commands.CommandManager;
import me.kisr.commands.Commands;
import me.kisr.listeners.ButtonInteraction;
import me.kisr.listeners.MessageRecieved;
import me.kisr.listeners.ModalInteraction;
import me.kisr.utils.FileUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {

    /*
    variables
     */

    public static JDA jda;
    public static Dotenv config = Dotenv.configure().load();

    /*
    main
     */

    public static void main(String[] args) {
        FileUtils.fileSetup();

        jda = JDABuilder.createDefault(config.get("TOKEN"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.watching("applications"))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new CommandManager(), new Commands(), new ButtonInteraction(), new ModalInteraction(), new MessageRecieved())
                .build();

    }
}