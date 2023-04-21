package Handlers;

import Config.ConfigUploader;
import io.github.classgraph.ClassGraph;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlashCommandHandler extends ListenerAdapter {

    private static final Map<String,Slash> slashmap = new HashMap<>();
    private final CommandListUpdateAction globalCommandsData;
    private final CommandListUpdateAction guildCommandsData;

    public SlashCommandHandler(JDA jda, Guild guild){
    this.globalCommandsData = jda.updateCommands();
    this.guildCommandsData = guild.updateCommands();


        ClassGraph classGraph = new ClassGraph();
        List<Slash> slashes = new ArrayList<>();
        classGraph.enableClassInfo()
                .scan()
                .getClassesImplementing(Slash.class)
                .forEach(classInfo -> {
                    try {
                    Slash slash = (Slash) classInfo.loadClass().getDeclaredConstructor().newInstance();
                    slashes.add(slash);
                    }catch(RuntimeException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e){
                        throw new RuntimeException("Unable to add slash with reason: " + e);
                    }
                });
        registerSlashCommands(slashes);
    }

    private  void registerSlashCommand(Slash slash){
        slashmap.put(slash.getName(), slash);

        if(slash.isGuildOnly()){
            guildCommandsData.addCommands(slash.getCommandData());
        }else
        {
            globalCommandsData.addCommands(slash.getCommandData());
        }

    }
    public void  registerSlashCommands(List<Slash> slashList){
        slashList.forEach(this::registerSlashCommand);
        queueCommand();
    }
    private void queueCommand(){
        globalCommandsData.queue();
        guildCommandsData.queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
       Slash slash = slashmap.get(event.getName());
        if (ConfigUploader.isCommandRestricted(event.getGuild().getId(), event.getName())) {
            String channelId = ConfigUploader.getRestrictedChannel(event.getGuild().getId(), event.getName());
            if (!event.getChannel().getId().equals(channelId)) {
                // Command is being executed in a restricted channel
                event.reply("This command is restricted to <#" + channelId + ">.").setEphemeral(true).queue();
                return;
            }
        }
        slash.onSlashCommandEvent(event);
    }
}
