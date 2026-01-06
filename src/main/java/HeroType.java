/**
 * Enum representing available hero types in the Dungeon Crawler.
 * Uses the Strategy pattern to define different hero archetypes.
 */
public enum HeroType {
    WIZARD(1, "Wizard", 5, 15, 8, "A master of arcane magic"),
    GOBLIN(2, "Goblin", 10, 8, 12, "A swift and cunning rogue"),
    KNIGHT(3, "Knight", 12, 10, 5, "A strong and sturdy warrior");

    private final int id;
    private final String name;
    private final int strength;
    private final int intelligence;
    private final int agility;
    private final String description;

    HeroType(int id, String name, int strength, int intelligence, int agility, String description) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAgility() {
        return agility;
    }

    public String getDescription() {
        return description;
    }

    public static HeroType fromId(int id) {
        for (HeroType type : HeroType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
}
