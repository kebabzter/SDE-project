/**
 * Represents a Hero character in the Dungeon Crawler.
 * Encapsulates hero properties and stats.
 */
public class Hero {
    private final String name;
    private final HeroType type;
    private int health;
    private int mana;
    private int level;

    public Hero(String playerName, HeroType type) {
        this.name = playerName;
        this.type = type;
        this.health = 100 + (type.getStrength() * 5);
        this.mana = 50 + (type.getIntelligence() * 5);
        this.level = 1;
    }

    public void displayStats() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          HERO STATS");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Name:         " + name);
        System.out.println("Class:        " + type.getName());
        System.out.println("Level:        " + level);
        System.out.println("Health:       " + health);
        System.out.println("Mana:         " + mana);
        System.out.println("\nAttributes:");
        System.out.println("  Strength:     " + type.getStrength());
        System.out.println("  Intelligence: " + type.getIntelligence());
        System.out.println("  Agility:      " + type.getAgility());
        System.out.println("═══════════════════════════════════════\n");
    }

    // Getters
    public String getName() {
        return name;
    }

    public HeroType getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getLevel() {
        return level;
    }
}
