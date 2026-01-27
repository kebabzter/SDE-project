package factory;

import model.Enemy;
import model.Goblin;

/**
 * Concrete Creator (Factory Method pattern).
 * Overrides the factory method to return Goblin products.
 */
public class GoblinCreator extends EnemyCreator {

    @Override
    public Enemy createEnemy(int level) {
        return new Goblin(level);
    }
}
