/**
 * Main entry point for The Pit - A Dungeon Crawler Game
 * 
 * This game demonstrates several design patterns:
 * - Singleton: Game class
 * - Factory Method: EnemyFactory and EnemyCreator hierarchy
 * - Strategy: EnemyAI for different combat behaviors
 * - State: HeroState for hero status effects
 * - Decorator: WeaponComponent and enchantments
 * - Composite: ItemContainer for inventory
 */
public class Main {
    public static void main(String[] args) {
        Game.getInstance().start();
    }
}
