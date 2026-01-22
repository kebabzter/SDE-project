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
    
    private EnemyAI strategy;
    
    public Enemy(String name, int health, int attack, int defense, int goldReward) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
        this.defense = defense;
        this.goldReward = goldReward;
        this.strategy = new AggressiveAI();
    }
    
    public void setStrategy(EnemyAI strategy) {
        this.strategy = strategy;
    }
    
    public EnemyAI getStrategy() {
        return strategy;
    }
    
    public EnemyAI.Action decideAction(int heroHealth, int heroMaxHealth) {
        return strategy.selectAction(this, heroHealth, heroMaxHealth);
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    public void applyRandomEffect(Hero hero) {
        int random = (int)(Math.random() * 100);
        
        if (random < 25) {
            hero.setState(new PoisonedState());
        } else if (random < 50) {
            hero.setState(new StunnedState());
        }
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
