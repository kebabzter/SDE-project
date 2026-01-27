package model;

import strategy.AggressiveAI;
import strategy.EnemyAI;

/**
 * Concrete Product (Factory Method pattern).
 * Implements the Enemy interface. Created by DemonCreator.
 *
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Factory Method – Refactoring.Guru</a>
 */
public class Demon implements Enemy {

    private static final int BASE_HEALTH = 30;
    private static final int BASE_ATTACK = 5;
    private static final int BASE_DEFENSE = 2;
    private static final int BASE_GOLD = 10;
    private static final double DIFFICULTY_MULTIPLIER = 1.4;

    private final String name;
    private int health;
    private final int maxHealth;
    private final int attack;
    private final int defense;
    private final int goldReward;
    private EnemyAI strategy;

    public Demon(int level) {
        this.name = "Demon";
        this.maxHealth = stat(BASE_HEALTH, level, 10);
        this.health = maxHealth;
        this.attack = stat(BASE_ATTACK, level, 2);
        this.defense = stat(BASE_DEFENSE, level, 1);
        this.goldReward = stat(BASE_GOLD, level, 5);
        this.strategy = new AggressiveAI();
    }

    private static int stat(int base, int level, double perLevel) {
        return (int) (base + level * perLevel * DIFFICULTY_MULTIPLIER);
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getHealth() { return health; }

    @Override
    public int getMaxHealth() { return maxHealth; }

    @Override
    public int getAttack() { return attack; }

    @Override
    public int getDefense() { return defense; }

    @Override
    public int getGoldReward() { return goldReward; }

    @Override
    public EnemyAI getStrategy() { return strategy; }

    @Override
    public void setHealth(int health) { this.health = health; }

    @Override
    public void setStrategy(EnemyAI strategy) { this.strategy = strategy; }
}
