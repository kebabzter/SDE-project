package factory;

import java.util.HashMap;
import java.util.Map;
import model.Enemy;

/**
 * Client for Factory Method pattern.
 * Selects creator type (by EnemyType) and delegates to concrete creators.
 * Works with creators and products only through abstract types (EnemyCreator, Enemy).
 * @see <a href="https://refactoring.guru/design-patterns/factory-method">Factory Method – Refactoring.Guru</a>
 */
public class EnemyFactory {
    
    public enum EnemyType {
        GOBLIN,
        SKELETON,
        ORC,
        DARK_MAGE,
        DEMON
    }
    
    private static final Map<EnemyType, EnemyCreator> creators = new HashMap<>();
    
    static {
        creators.put(EnemyType.GOBLIN, new GoblinCreator());
        creators.put(EnemyType.SKELETON, new SkeletonCreator());
        creators.put(EnemyType.ORC, new OrcCreator());
        creators.put(EnemyType.DARK_MAGE, new DarkMageCreator());
        creators.put(EnemyType.DEMON, new DemonCreator());
    }
    
    public static Enemy createEnemy(EnemyType type, int level) {
        EnemyCreator creator = creators.get(type);
        
        if (creator == null) {
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
        
        return creator.createEnemy(level);
    }
    
    public static EnemyCreator getCreator(EnemyType type) {
        return creators.get(type);
    }
    
    public static Enemy createRandomEnemy(int level) {
        EnemyType[] types = EnemyType.values();
        EnemyType randomType = types[(level - 1) % types.length];
        return createEnemy(randomType, level);
    }
    
    public static Enemy createEnemyByIndex(int typeIndex, int level) {
        EnemyType[] types = EnemyType.values();
        EnemyType type = types[typeIndex % types.length];
        return createEnemy(type, level);
    }
    
    public static EnemyType[] getAllEnemyTypes() {
        return EnemyType.values();
    }
    
    public static void registerCreator(EnemyType type, EnemyCreator creator) {
        creators.put(type, creator);
    }
}
