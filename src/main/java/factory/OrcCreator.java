package factory;

import model.Enemy;
import model.Orc;

/**
 * Concrete Creator (Factory Method pattern).
 * Overrides the factory method to return Orc products.
 */
public class OrcCreator extends EnemyCreator {

    @Override
    public Enemy createEnemy(int level) {
        return new Orc(level);
    }
}
