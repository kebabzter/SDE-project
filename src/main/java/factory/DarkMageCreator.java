package factory;

import model.Enemy;
import model.DarkMage;

/**
 * Concrete Creator (Factory Method pattern).
 * Overrides the factory method to return Dark Mage products.
 */
public class DarkMageCreator extends EnemyCreator {

    @Override
    public Enemy createEnemy(int level) {
        return new DarkMage(level);
    }
}
