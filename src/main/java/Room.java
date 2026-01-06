import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room/level in the dungeon.
 * Each room contains enemies that must be defeated.
 */
public class Room {
    private final int level;
    private final List<Enemy> enemies;
    private boolean cleared;
    
    public Room(int level) {
        this.level = level;
        this.enemies = new ArrayList<>();
        this.cleared = false;
    }
    
    /**
     * Adds an enemy to this room.
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * Gets the next alive enemy in the room.
     */
    public Enemy getNextEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return enemy;
            }
        }
        return null;
    }
    
    /**
     * Checks if all enemies in the room are defeated.
     */
    public boolean isCleared() {
        if (cleared) return true;
        
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return false;
            }
        }
        cleared = true;
        return true;
    }
    
    /**
     * Displays room information.
     */
    public void displayInfo() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         LEVEL " + level + "                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Enemies remaining: " + getRemainingEnemyCount());
    }
    
    /**
     * Counts remaining alive enemies.
     */
    public int getRemainingEnemyCount() {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }
    
    // Getters
    public int getLevel() {
        return level;
    }
    
    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }
}

