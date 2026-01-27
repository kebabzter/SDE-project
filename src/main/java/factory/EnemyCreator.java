package factory;

import model.Enemy;

/**
 * Creator (Factory Method pattern).
 * Declares the factory method that returns new product objects.
 * Subclasses override it to return different concrete products.
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Factory Method – Refactoring.Guru</a>
 */
public abstract class EnemyCreator {

    /**
     * Factory method – subclasses override to create specific concrete products.
     * Return type matches the product interface (Enemy).
     */
    public abstract Enemy createEnemy(int level);
}
