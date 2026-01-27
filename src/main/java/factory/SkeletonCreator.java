package factory;

import model.Enemy;
import model.Skeleton;

/**
 * Concrete Creator (Factory Method pattern).
 * Overrides the factory method to return Skeleton products.
 */
public class SkeletonCreator extends EnemyCreator {

    @Override
    public Enemy createEnemy(int level) {
        return new Skeleton(level);
    }
}
