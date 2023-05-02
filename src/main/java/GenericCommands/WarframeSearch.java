package GenericCommands;

import Handlers.Slash;
import Handlers.WFData;
import Warframe.WarframParse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class WarframeSearch implements Slash {
    private final WarframParse warframParse;

    public WarframeSearch() {
        warframParse = new WarframParse();
    }


    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        String wfName = event.getOption("warframe").getAsString();
        WFData wfData = warframParse.retriveWFData(wfName);

        if(wfData == null){
            event.reply("No data found for warframe: " + wfName).queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle(wfData.getName())
                .setDescription(wfData.getDescription())
                .addField("Health", String.valueOf(wfData.getHealth()), true)
                .addField("Shield", String.valueOf(wfData.getShield()),true)
                .addField("Energy", String.valueOf(wfData.getEnergy()), true)
                .addField("Sprint Speed", String.format("%2f", wfData.getSprintSpeed()), true)
                .addField("Abilities", Arrays.toString(wfData.getAbilities()), false)
                .addField("Release-Date", wfData.getReldate(), true)
                .setThumbnail(wfData.getThumbNail());

        event.replyEmbeds(builder.build()).queue();



    }

    @Override
    public String getName() {
        return "wfsearch";
    }

    @Override
    public String getDescription() {
        return "Allows a user to utilize the warframe api to search for a warframe!";
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
                new OptionData(OptionType.STRING, "warframe", "Search for a warframe")
                        .setRequired(true)
        );
    }
}
