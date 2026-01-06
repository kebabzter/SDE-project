public class Enemy {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int attack;
    private final int defense;
    private final int goldReward;
    
    public Enemy(String name, int health, int attack, int defense, int goldReward) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.defense = defense;
        this.goldReward = goldReward;
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public void displayStatus() {
        System.out.println(name + " - HP: " + health + "/" + maxHealth + " | ATK: " + attack + " | DEF: " + defense);
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public int getGoldReward() {
        return goldReward;
    }
}

