package factory;

import model.Enemy;
import strategy.SmartAI;

/**
 * Factory Method Pattern - Concrete Creator for Dark Mage enemies
 * 
 * This concrete creator implements the factory method to produce Dark Mage enemies.
 * Dark Mages are intelligent spellcasters who use tactical magic in combat.
 */
public class DarkMageCreator extends EnemyCreator {
    
    // Dark Mages are moderately difficult
    private static final double DIFFICULTY_MULTIPLIER = 1.1;
    
    /**
     * Factory Method implementation - creates a Dark Mage enemy.
     * Dark Mages use smart AI - they're calculating spellcasters.
     * 
     * @param level The difficulty level affecting stats
     * @return A new Dark Mage Enemy instance with SmartAI
     */
    @Override
    public Enemy createEnemy(int level) {
        // Dark Mages are calculating and strategic
        return createConfiguredEnemy(level, new SmartAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Dark Mage";
    }
    
    @Override
    public double getDifficultyMultiplier() {
        return DIFFICULTY_MULTIPLIER;
    }
}
