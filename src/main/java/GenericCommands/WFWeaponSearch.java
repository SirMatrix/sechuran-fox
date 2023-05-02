package GenericCommands;

import Handlers.ItemData;
import Handlers.Slash;
import Warframe.WeaponParse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WFWeaponSearch implements Slash {
    private final WeaponParse weaponParse;

    public WFWeaponSearch() {
        this.weaponParse = new WeaponParse();
    }
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {

        String weaponName = event.getOption("weapon").getAsString();

        ItemData itemData = weaponParse.retrieveItemData(weaponName);



        if (itemData == null) {
            event.reply("No data found for weapon: " + weaponName).queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.BLUE)
                .setTitle(itemData.getName())
                .setDescription(itemData.getDescription())
                .addField("Type", itemData.getType(), true)
                .addField("Mastery Requirement", String.valueOf(itemData.getMasteryReq()), true)
                .addField("Damage", Arrays.toString(itemData.getDamage()), false)
                .addField("Critical Chance", String.format("%.2f%%", itemData.getCriticalChance()), true)
                .addField("Critical Multiplier", String.format("%.2f", itemData.getCriticalMultiplier()), true)
                .addField("Status Chance", String.format("%.2f%%", itemData.getProcChance()), true)
                .addField("Fire Rate", String.format("%.2f", itemData.getFireRate()), true)
                .addField("Magazine Size", String.valueOf(itemData.getMagazineSize()), true)
                .addField("Reload Time", String.format("%.2fs", itemData.getReloadTime()), true)
                .setThumbnail(itemData.wikiaThumbnail());



        event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "wfwsearch";
    }

    @Override
    public String getDescription() {
        return "Allows a user to utilize the warframe api to search for weapons!";
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
                new OptionData(OptionType.STRING, "weapon", "Weapon to find info about")
                        .setRequired(true)

        );
    }
}
