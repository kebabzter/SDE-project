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
    
    /**
     * Applies a random status effect to the target hero
     * 25% poison, 25% stun, 50% no effect
     */
    public void applyRandomEffect(Hero hero) {
        int random = (int)(Math.random() * 100);
        
        if (random < 25) {
            hero.setState(new PoisonedState());
        } else if (random < 50) {
            hero.setState(new StunnedState());
        }
        // Otherwise (50-100), no effect
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

