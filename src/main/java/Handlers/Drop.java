package Handlers;

public class Drop {

    private double chance;
    private String rarity;
    private String location;
    private String type;

    public Drop( double chance, String rarity, String location, String type) {
        this.chance = chance;
        this.rarity = rarity;
        this.location = location;
        this.type = type;
    }



    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
