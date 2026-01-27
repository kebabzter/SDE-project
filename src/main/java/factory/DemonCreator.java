package factory;

import model.Enemy;
import model.Demon;

/**
 * Concrete Creator (Factory Method pattern).
 * Overrides the factory method to return Demon products.
 */
public class DemonCreator extends EnemyCreator {

    @Override
    public Enemy createEnemy(int level) {
        return new Demon(level);
    }
}
