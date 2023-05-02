package Handlers;

public class WFData {
    private final String name;
    private final String description;
    private final int power;
    private final int shield;
    private final String reldate;
    private final double sprintSpeed;
    private final int health;
    private final String thumbNail;
    private final String[] abilities;

    public WFData(String name, String description, int power, int shield, String reldate, double sprintSpeed, int health, String thumbNail, String[] abilities) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.shield = shield;
        this.reldate = reldate;
        this.sprintSpeed = sprintSpeed;
        this.health = health;
        this.thumbNail = thumbNail;
        this.abilities = abilities;

    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }
    public int getEnergy(){
        return power;
    }
    public int getShield(){
        return shield;
    }
    public String getReldate(){
        return reldate;
    }
    public double getSprintSpeed(){
        return sprintSpeed;
    }
    public int getHealth(){
        return health;
    }
    public String getThumbNail(){
        return thumbNail;
    }
    public String[] getAbilities(){
        return abilities;
    }
}
