package Handlers;

public class ModData {
    private  final String name;
    private final String rarity;
    private final String thumbNail;
    private  final  String polarity;
    private final String type;

    public ModData(String name, String rarity, String thumbNail, String polarity, String type) {
        this.name = name;
        this.rarity = rarity;
        this.thumbNail = thumbNail;
        this.polarity = polarity;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public  String getRarity(){return  rarity;}
    public String getThumbNail(){return  thumbNail;}
    public  String getPolarity(){return  polarity;}
    public  String getType(){return type;}

}
