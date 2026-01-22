package model;

import state.PoisonedState;
import state.StunnedState;
import strategy.AggressiveAI;
import strategy.EnemyAI;

public class Enemy {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int attack;
    private final int defense;
    private final int goldReward;
    
    // Strategy pattern for AI behavior
    private EnemyAI strategy;
    
    public Enemy(String name, int health, int attack, int defense, int goldReward) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.defense = defense;
        this.goldReward = goldReward;
        
        // Default to aggressive strategy
        this.strategy = new AggressiveAI();
    }
    
    /**
     * Sets the AI strategy for this enemy
     * @param strategy The EnemyAI strategy to use
     */
    public void setStrategy(EnemyAI strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Gets the current AI strategy
     * @return The EnemyAI strategy being used
     */
    public EnemyAI getStrategy() {
        return strategy;
    }
    
    /**
     * Decides what action to take in combat using the strategy
     * @param heroHealth The hero's current health
     * @param heroMaxHealth The hero's maximum health
     * @return The action this enemy should take
     */
    public EnemyAI.Action decideAction(int heroHealth, int heroMaxHealth) {
        return strategy.selectAction(this, heroHealth, heroMaxHealth);
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    /**
     * Heals the enemy by the specified amount
     * @param amount The amount to heal
     */
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    /**
     * Applies a random status effect to the target hero.
     * 25% poison, 25% stun, 50% no effect.
     * 
     * STATE PATTERN: Creating new state instances and setting them via
     * context's setState() method. The state will receive context reference
     * automatically and can manage its own lifecycle/transitions.
     */
    public void applyRandomEffect(Hero hero) {
        int random = (int)(Math.random() * 100);
        
        if (random < 25) {
            // State pattern: create new state, context will set itself as reference
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
