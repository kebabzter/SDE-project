package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int level;
    private final List<Enemy> enemies;
    private boolean cleared;
    
    public Room(int level) {
        this.level = level;
        this.enemies = new ArrayList<>();
        this.cleared = false;
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public Enemy getNextEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return enemy;
            }
        }
        return null;
    }
    
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
    
    public void displayInfo() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║         LEVEL " + level + "                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Enemies remaining: " + getRemainingEnemyCount());
    }
    
    public int getRemainingEnemyCount() {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }
    
    public int getLevel() {
        return level;
    }
    
    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }
}
