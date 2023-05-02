package GenericCommands;

import Events.MessageListener;
import Handlers.Drop;
import Handlers.ModData;
import Handlers.Slash;
import Misc.ExcelWriter;
import Warframe.ModParse;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.modals.Modal.Builder.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Config.FileLocations;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WFModSearch extends ListenerAdapter implements Slash {
    private  final ModParse modParse;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);
   public WFModSearch(){modParse = new ModParse();}



    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
       String modName = event.getOption("mod").getAsString();
        ModData modData = modParse.retrieveModData(modName);

        if(modData == null){
            event.reply("No data found for mod: " + modName).queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle(modData.getName())
                .addField("Rarity", modData.getRarity(), true)
                .addField("Polarity", modData.getPolarity(), true)
                .addField("Type", modData.getType(), true)
                .setThumbnail(modData.getThumbNail());

        Button button = Button.primary("generate_excel" + modName, "Generate Excel Sheet");
        List<Button> components = Collections.singletonList(button);
        ActionRow row = ActionRow.of(components);
        event.replyEmbeds(builder.build()).addComponents(row).queue();

    }
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        String modName = componentId.split("generate_excel")[1];
        MessageChannel channel = event.getChannel();

        if (event.getComponentId().equals(componentId)) {
            // Generate the excel sheet and upload it
            List<Drop> dropsList = null;
            try {
                LOGGER.info("Retrieving drops for mod: {}", modName);
                dropsList = modParse.retrieveModDrops(modName);
                LOGGER.info("Retrieved {} drops for mod: {}", dropsList.size(), modName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            LOGGER.info("Generating Excel file for mod: {}", modName);
            ExcelWriter.writeExcel(dropsList);
            LOGGER.info("Excel file generated for mod: {}", modName);
            File file = new File(FileLocations.DROP_PATH);

            try {
                // Upload file to channel
                channel.sendFiles(FileUpload.fromData(file)).queue();
                LOGGER.info("Excel file uploaded to channel for mod: {}", modName);

                // Remove button by deleting the original message that contained it
                Message message = event.getMessage();
                message.delete().queue();
            } catch (Exception e) {
                LOGGER.error("Error uploading Excel file for mod: {}", modName, e);
            } finally {
                file.delete();
                event.reply("Here is the drop list for: " + modName + ", I have removed the embed to prevent file spam!").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "wfmodsearch";
    }

    @Override
    public String getDescription() {
        return "Search for a specific mod in warframe!";
    }

    @Override
    public boolean isSpecifiedGuildOnly() {
        return false;
    }

    @Override
    public boolean isGuildOnly() {
        return true;
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "mod", "Type what mod you want to seach for")
                        .setRequired(true)
        );
    }
}
