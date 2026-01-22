package factory;

import model.Enemy;
import strategy.SmartAI;

/**
 * Concrete Creator for Dark Mage enemies
 */
public class DarkMageCreator extends EnemyCreator {
    
    private static final double DIFFICULTY_MULTIPLIER = 1.1;
    
    @Override
    public Enemy createEnemy(int level) {
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
