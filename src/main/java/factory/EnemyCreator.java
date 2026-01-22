package factory;

import model.Enemy;
import strategy.EnemyAI;

/**
 * Factory Method Pattern - Abstract Creator
 * 
 * Defines the factory method that subclasses override to create specific enemy types.
 * Each concrete creator (GoblinCreator, SkeletonCreator, etc.) implements createEnemy()
 * to produce its specific enemy type with appropriate stats and AI.
 */
public abstract class EnemyCreator {
    
    protected static final int BASE_HEALTH = 30;
    protected static final int BASE_ATTACK = 5;
    protected static final int BASE_DEFENSE = 2;
    protected static final int BASE_GOLD = 10;
    
    /**
     * Factory Method - subclasses override to create specific enemies
     */
    public abstract Enemy createEnemy(int level);
    
    public abstract String getEnemyTypeName();
    
    public abstract double getDifficultyMultiplier();
    
    protected int calculateScaledStat(int baseStat, int level, double perLevelBonus) {
        return (int) (baseStat + (level * perLevelBonus * getDifficultyMultiplier()));
    }
    
    protected Enemy createConfiguredEnemy(int level, EnemyAI strategy) {
        int health = calculateScaledStat(BASE_HEALTH, level, 10);
        int attack = calculateScaledStat(BASE_ATTACK, level, 2);
        int defense = calculateScaledStat(BASE_DEFENSE, level, 1);
        int gold = calculateScaledStat(BASE_GOLD, level, 5);
        
        Enemy enemy = new Enemy(getEnemyTypeName(), health, attack, defense, gold);
        enemy.setStrategy(strategy);
        return enemy;
    }
}
