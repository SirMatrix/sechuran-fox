package Handlers;

public class ItemData {
    private final String name;
    private final String description;
    private final String type;
    private final int masteryReq;
    private final double[] damage;
    private final double criticalChance;
    private final double criticalMultiplier;
    private final double procChance;
    private final double fireRate;
    private final int magazineSize;
    private final double reloadTime;
    private final String wikiaThumbnail;


    public ItemData(String name, String description, String type, int masteryReq, double[] damage, double criticalChance, double criticalMultiplier, double procChance, double fireRate, int magazineSize, double reloadTime, String wikiaThumbnail) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.masteryReq = masteryReq;
        this.damage = damage;
        this.criticalChance = criticalChance;
        this.criticalMultiplier = criticalMultiplier;
        this.procChance = procChance;
        this.fireRate = fireRate;
        this.magazineSize = magazineSize;
        this.reloadTime = reloadTime;
        this.wikiaThumbnail = wikiaThumbnail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getMasteryReq() {
        return masteryReq;
    }

    public double[] getDamage() {return damage;}

    public double getCriticalChance() {
        return criticalChance;
    }

    public double getCriticalMultiplier() {
        return criticalMultiplier;
    }

    public double getProcChance() {
        return procChance;
    }

    public double getFireRate() {
        return fireRate;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public String wikiaThumbnail(){return wikiaThumbnail;}


    // getters and setters for all fields
}
