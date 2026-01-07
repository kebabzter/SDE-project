# The Pit - Dungeon Crawler

A text-based dungeon crawler game demonstrating multiple design patterns in Java.

## Game Overview

**The Pit** is an endless survival dungeon crawler where you:
- Choose a hero class (Goblin, Knight, or Wizard)
- Fight increasingly difficult enemies
- Earn gold to buy upgrades in the shop
- Try to survive as deep as possible

---

## Design Patterns Implemented

This project demonstrates **6 design patterns** from **3 different categories**:
- **2 Creational Patterns:** Singleton, Factory
- **2 Structural Patterns:** Decorator, Composite
- **1 Behavioral Pattern:** State

---

## 1. SINGLETON PATTERN (Creational)

### Where It's Used
**File:** `Game.java`

### What It Does
Ensures only one instance of the Game class exists throughout the entire application.

### How It Works

```java
public class Game {
    private static Game instance;
    
    // Private constructor prevents external instantiation
    private Game() {
        this.scanner = new Scanner(System.in);
    }
    
    // Thread-safe lazy initialization
    public static Game getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
    }
}
```

### Usage in Code

**Main.java:**
```java
public static void main(String[] args) {
    // Get the singleton instance
    Game.getInstance().start();
}
```

### Where to See It
- Look at `Main.java` - uses `Game.getInstance()` instead of `new Game()`
- Only one game instance manages all game state throughout execution

### Benefits
 **Single Source of Truth** - All game state in one place  
 **Global Access** - Any part of code can access the game  
 **Resource Control** - Prevents multiple Scanner instances  
 **State Consistency** - No conflicting game states  

### Why This Pattern?
Without Singleton, multiple Game objects could exist with different states, causing bugs and confusion. The Singleton pattern guarantees one coordinated game instance.

---

## 2. FACTORY PATTERN (Creational)

### Where It's Used
**File:** `EnemyFactory.java`

### What It Does
Encapsulates the creation of different enemy types, centralizing enemy instantiation logic and making it easy to add new enemy types without modifying existing code.

### How It Works

```java
// Factory with enum for enemy types
public class EnemyFactory {
    public enum EnemyType {
        GOBLIN("Goblin", 0.8),
        SKELETON("Skeleton", 1.0),
        ORC("Orc", 1.2),
        DARK_MAGE("Dark Mage", 1.1),
        DEMON("Demon", 1.4);
        
        private final String displayName;
        private final double difficultyMultiplier;
        
        EnemyType(String displayName, double multiplier) {
            this.displayName = displayName;
            this.difficultyMultiplier = multiplier;
        }
    }
    
    // Factory method creates enemies with scaled stats
    public static Enemy createEnemy(EnemyType type, int level) {
        double multiplier = type.getDifficultyMultiplier();
        
        int scaledHealth = (int)(30 + (level * 10 * multiplier));
        int scaledAttack = (int)(5 + (level * 2 * multiplier));
        int scaledDefense = (int)(2 + (level * multiplier));
        int scaledGold = (int)(10 + (level * 5 * multiplier));
        
        return new Enemy(
            type.getDisplayName(),
            scaledHealth,
            scaledAttack,
            scaledDefense,
            scaledGold
        );
    }
    
    // Create random enemy or by index
    public static Enemy createRandomEnemy(int level) { ... }
    public static Enemy createEnemyByIndex(int typeIndex, int level) { ... }
}
```

### Usage in Code

**Game.java (generateRoom method):**
```java
private Room generateRoom(int level) {
    Room room = new Room(level);
    
    int enemyCount = 1 + (level / 3);
    for (int i = 0; i < enemyCount; i++) {
        // Factory creates varied enemies by index
        Enemy enemy = EnemyFactory.createEnemyByIndex(i, level);
        room.addEnemy(enemy);
    }
    
    return room;
}
```

### Where to See It
1. Start the game and enter any room
2. Notice enemies have varied types (Goblin, Skeleton, Orc, Dark Mage, Demon)
3. As you progress to higher levels, each enemy type scales differently
4. Example at Level 5:
   - Goblin: weak but nimble (multiplier 0.8)
   - Demon: deadly and strong (multiplier 1.4)

### Benefits
 **Centralized Creation Logic** - All enemy creation in one place  
 **Easy to Extend** - Add new enemy types by extending the enum  
 **Type Safety** - EnemyType enum prevents invalid enemy names  
 **Scalable Stats** - Each type has unique difficulty multiplier  
 **Reduces Code Duplication** - No scattered enemy creation code  

### Why This Pattern?
Instead of having `createGoblin()`, `createSkeleton()`, `createOrc()` methods scattered throughout the code, the Factory pattern provides a single point of creation. Adding a new enemy type only requires adding it to the enum—the factory method handles everything else.

---

## 3. DECORATOR PATTERN (Structural)

### Where It's Used
**Files:** `Weapon.java`, `EnchantedWeapon.java`

### What It Does
Dynamically adds enchantment bonuses to weapons without modifying the original Weapon class.

### How It Works

```java
// Base Component
public class Weapon {
    protected String name;
    protected int damage;
    
    public int getDamage() {
        return damage;
    }
}

// Decorator wraps the component
public class EnchantedWeapon extends Weapon {
    private final Weapon wrappedWeapon;
    private final String enchantmentName;
    private final int enchantmentBonus;
    
    public EnchantedWeapon(Weapon weapon, String enchantmentName, int bonus) {
        this.wrappedWeapon = weapon;
        this.enchantmentName = enchantmentName;
        this.enchantmentBonus = bonus;
    }
    
    @Override
    public int getDamage() {
        // Add bonus to wrapped weapon's damage
        return wrappedWeapon.getDamage() + enchantmentBonus;
    }
    
    @Override
    public String getName() {
        return enchantmentName + " " + wrappedWeapon.getName();
    }
}
```

### Usage in Code

**Hero.java:**
```java
// Hero starts with basic weapon
private Weapon weapon = new Weapon("Rusty Sword", 5); // +5 damage

public int getAttack() {
    return baseAttack + weapon.getDamage();
}
```

**Shop.java (enchantWeapon method):**
```java
// Wrap current weapon with enchantment
Weapon currentWeapon = hero.getWeapon();
EnchantedWeapon enchanted = new EnchantedWeapon(currentWeapon, "Flame", 7);
hero.equipWeapon(enchanted);

// Result: "Flame Rusty Sword" with 5 + 7 = 12 damage
```

### Where to See It
1. Start the game - hero has "Rusty Sword (+5 ATK)"
2. Earn 80 gold (defeat ~3-4 enemies)
3. Visit shop → Select **[5] Enchant Weapon**
4. See transformation:
   ```
   Before: Rusty Sword (+5 ATK)
   After:  Flame Rusty Sword (+12 ATK) [Enchanted: +7]
   ```
5. Check hero stats to see combined attack damage

### Benefits
 **Open/Closed Principle** - Add features without modifying Weapon class  
 **Flexible Enhancement** - Add/remove bonuses dynamically at runtime  
 **Stackable** - Can wrap decorators around decorators for multiple enchantments  
 **No Subclass Explosion** - Don't need FlameWeapon, IceWeapon, etc. classes  

### Why This Pattern?
Instead of creating subclasses for every weapon variation (FlameRustySword, IceIronSword, etc.), we can dynamically "decorate" any weapon with any enchantment. This keeps code maintainable and flexible.

---

## 4. STATE PATTERN (Behavioral)

### Where It's Used
**Files:** `HeroState.java`, `NormalState.java`, `PoisonedState.java`, `StunnedState.java`, `Hero.java`

### What It Does
Allows a hero to change behavior dynamically based on status effects (Normal, Poisoned, Stunned). The hero's attack power, defense, and ability to act depend on its current state.

### How It Works

```java
// State Interface - defines behavior for all states
public interface HeroState {
    void onEnter(Hero hero);
    void onExit(Hero hero);
    int modifyAttack(int baseAttack);
    int modifyDefense(int baseDefense);
    boolean canAct();
    String getStateName();
    String getStateDescription();
    boolean decrementDuration();
}

// Concrete State: Normal
public class NormalState implements HeroState {
    public int modifyAttack(int baseAttack) {
        return baseAttack; // No modification
    }
    public boolean canAct() {
        return true; // Can always act
    }
}

// Concrete State: Poisoned (3 turns, -40% attack)
public class PoisonedState implements HeroState {
    private int turnsRemaining = 3;
    public int modifyAttack(int baseAttack) {
        return (int)(baseAttack * 0.6); // 40% reduction
    }
    public boolean canAct() {
        return true; // Can still act
    }
}

// Concrete State: Stunned (2 turns, cannot act)
public class StunnedState implements HeroState {
    private int turnsRemaining = 2;
    public int modifyAttack(int baseAttack) {
        return 0; // Cannot attack
    }
    public boolean canAct() {
        return false; // Fully incapacitated
    }
}
```

### Usage in Code

**Hero.java (state management):**
```java
public class Hero {
    private HeroState currentState; // Holds the current state
    
    public int getAttack() {
        // Delegate to state
        return currentState.modifyAttack(baseAttack + weapon.getDamage());
    }
    
    public int getDefense() {
        // Delegate to state
        return currentState.modifyDefense(defense);
    }
    
    public void setState(HeroState newState) {
        currentState.onExit(this);
        this.currentState = newState;
        currentState.onEnter(this);
    }
}
```

**Game.java (combat with states):**
```java
private void combat(Enemy enemy) {
    while (enemy.isAlive() && hero.isAlive()) {
        // Check if hero can act
        if (!hero.canAct()) {
            System.out.println("⭐ You are stunned and cannot act!");
        }
        
        // Apply damage based on current state
        int damage = hero.getAttack(); // Uses modified attack
        enemy.takeDamage(damage);
        
        // Enemy randomly applies effects
        enemy.applyRandomEffect(hero); // Hero state may change!
        
        // Update state duration
        hero.updateState(); // Transitions to Normal if duration expires
    }
}
```

### Where to See It
1. Start the game and enter combat
2. Watch for status effect messages:
   ```
   ☠ [Hero] has been POISONED! (lasts 3 turns)
   ⭐ [Hero] has been STUNNED! (lasts 2 turns)
   ✓ [Hero] returns to normal!
   ```
3. Notice:
   - **Poisoned:** Your attack drops by 40%, and you take 5 damage/turn for 3 turns
   - **Stunned:** You cannot attack for 2 turns, defense is halved
   - **Normal:** Full abilities, no penalties

### Benefits
 **Encapsulation** - Each state contains its own behavior logic  
 **Easy State Transitions** - Switch states cleanly with `setState()`  
 **Open/Closed Principle** - Add new states without modifying existing ones  
 **Single Responsibility** - Each state class handles one state's behavior  
 **Runtime Behavior Change** - Hero behavior changes dynamically during combat  

### Why This Pattern?
Without the State pattern, Hero would need massive conditional logic:
```java
if (isPoisoned && poisonTurnsLeft > 0) {
    attack = (int)(attack * 0.6);
    health -= 5;
    poisonTurnsLeft--;
    if (poisonTurnsLeft == 0) isPoisoned = false;
}
if (isStunned && stunTurnsLeft > 0) {
    canAct = false;
    defense = defense / 2;
    stunTurnsLeft--;
    if (stunTurnsLeft == 0) isStunned = false;
}
// ... similar for every state
```

The State pattern elegantly handles this by delegating to state objects.

---

## 5. COMPOSITE PATTERN (Structural)

### Where It's Used
**Files:** `Item.java`, `SimpleItem.java`, `ItemContainer.java`

### What It Does
Creates a tree structure where containers can hold items OR other containers, treating both uniformly.

### How It Works

```java
// Component Interface - treats items and containers uniformly
public interface Item {
    String getName();
    int getValue();
    String getDescription();
    boolean isContainer();
}

// Leaf - Simple item
public class SimpleItem implements Item {
    private final String name;
    private final int value;
    
    public int getValue() {
        return value; // Just return own value
    }
    
    public boolean isContainer() {
        return false; // Not a container
    }
}

// Composite - Container that holds Items (including other containers!)
public class ItemContainer implements Item {
    private final List<Item> items;
    
    public boolean addItem(Item item) {
        items.add(item); // Can add SimpleItem OR ItemContainer
    }
    
    @Override
    public int getValue() {
        // Recursively sum all contained items
        return items.stream()
            .mapToInt(Item::getValue)
            .sum();
    }
    
    @Override
    public boolean isContainer() {
        return true; // Is a container
    }
}
```

### Usage in Code

**Hero.java initialization:**
```java
// Create a backpack (ItemContainer)
this.inventory = new ItemContainer("Backpack", 10);

// Add a simple item (SimpleItem)
inventory.addItem(new SimpleItem("Health Potion", 20, "Restores 30 HP"));

// Could also add another container inside!
ItemContainer pouch = new ItemContainer("Gold Pouch", 5);
inventory.addItem(pouch); // Container inside container!
```

**ItemContainer.java (recursive display):**
```java
public void displayContents(int indent) {
    System.out.println("+ " + getName() + " (" + items.size() + "/" + capacity + ")");
    
    for (Item item : items) {
        if (item.isContainer()) {
            ((ItemContainer) item).displayContents(indent + 1); // RECURSIVE!
        } else {
            System.out.println("  - " + item.getName());
        }
    }
}
```

### Where to See It
1. Start the game - hero starts with "Backpack" inventory
2. Earn some gold and visit shop
3. Select **[6] View Inventory**
4. See hierarchical tree structure:
   ```
   + Backpack (1/10)
     - Health Potion (20g)
   
   Total value: 20g
   ```

The "Total value" is calculated recursively - if you had pouches inside the backpack with items inside the pouches, it would sum everything!

### Benefits
 **Uniform Treatment** - Same interface for items and containers  
 **Natural Hierarchy** - Backpack → Pouch → Coin Purse → Items  
 **Recursive Operations** - Calculate total value, display all, search all  
 **Flexible Structure** - Add/remove containers and items freely  

### Why This Pattern?
In a real game, you might have:
- Backpack (holds everything)
  - Gold Pouch (holds money)
    - Gold Coins
    - Silver Coins
  - Weapon Rack (holds weapons)
    - Sword
    - Dagger
  - Health Potion

The Composite pattern lets you treat all of this uniformly with one interface, and operations like "count total value" work recursively through the entire tree.

---

## Pattern Summary Table

| Pattern | Category | Files | Line Count | Demonstrated In |
|---------|----------|-------|------------|-----------------|
| **Singleton** | Creational | `Game.java`, `Main.java` | ~40 | Game instance management |
| **Factory** | Creational | `EnemyFactory.java`, `Game.java` | ~80 | Room generation - Varied enemy types |
| **Decorator** | Structural | `Weapon.java`, `EnchantedWeapon.java` | ~60 | Shop option [5] - Weapon enchanting |
| **State** | Behavioral | `HeroState.java`, `NormalState.java`, `PoisonedState.java`, `StunnedState.java`, `Hero.java` | ~150 | Combat - Hero status effects |
| **Composite** | Structural | `Item.java`, `SimpleItem.java`, `ItemContainer.java` | ~120 | Shop option [6] - Inventory system |

---

## How to Run the Game

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Compile & Run
```bash
# Compile the project
mvn compile

# Run the game
mvn exec:java
```

### Quick Start
1. **Character Creation:** Choose your hero class (Warrior, Mage, or Rogue)
2. **Combat:** Fight enemies that appear in each room
   - Choose [1] Attack or [2] Defend each turn
3. **Shop:** After clearing a room, visit the shop to:
   - Buy health potions and stat upgrades
   - **[5] Enchant Weapon** - See Decorator pattern in action!
   - **[6] View Inventory** - See Composite pattern in action!
4. **Survive:** Each level gets harder - see how deep you can go!

---

## Project Structure

```
src/main/java/
├── Main.java              # Entry point (uses Singleton)
├── Game.java              # SINGLETON - game state manager (uses FACTORY and STATE)
├── Hero.java              # Player character (uses STATE pattern)
├── HeroType.java          # Hero class definitions
├── Enemy.java             # Enemy entities (applies STATE effects)
├── EnemyFactory.java      # FACTORY - creates different enemy types
├── Room.java              # Room/level container
├── Shop.java              # Shop system
│
├── Weapon.java            # DECORATOR - base weapon
├── EnchantedWeapon.java   # DECORATOR - weapon decorator
│
├── HeroState.java         # STATE - state interface
├── NormalState.java       # STATE - normal status
├── PoisonedState.java     # STATE - poisoned status
├── StunnedState.java      # STATE - stunned status
│
├── Item.java              # COMPOSITE - component interface
├── SimpleItem.java        # COMPOSITE - leaf node
└── ItemContainer.java     # COMPOSITE - composite node
```

---

## Testing the Patterns

### Test Singleton
```bash
# Run the game and check Main.java
# Notice it uses Game.getInstance() - only one instance created
```

### Test Factory
```bash
# 1. Start the game
# 2. Enter rooms at different levels
# 3. Observe different enemy types appearing:
#    - Level 1: Goblin (weak, 0.8x multiplier)
#    - Level 2: Skeleton (standard, 1.0x multiplier)
#    - Level 3: Orc (tougher, 1.2x multiplier)
#    - Level 4: Dark Mage (threatening, 1.1x multiplier)
#    - Level 5+: Demon (deadly, 1.4x multiplier)
# 4. Notice multi-enemy rooms have varied types
# 5. Check enemy stats scale based on level and type
```

### Test State
```bash
# 1. Start game and enter combat
# 2. Fight multiple enemies and watch for status effects:
#    ☠ You are POISONED! (reduced attack for 3 turns)
#    ⭐ You are STUNNED! (cannot act for 2 turns)
# 3. Check hero status:
#    - Poisoned: Attack is reduced by 40%, take 5 damage/turn
#    - Stunned: Cannot perform actions, defense halved
# 4. Watch effects expire:
#    ✓ [Hero] returns to normal!
# 5. Verify state transitions in combat UI
```

### Test Decorator
```bash
# 1. Start game
# 2. Fight enemies until you have 80+ gold
# 3. Visit shop → [5] Enchant Weapon
# 4. Check hero stats - weapon now has "Flame" prefix and +7 bonus
# 5. Can enchant again to stack bonuses! (Flame Flame Rusty Sword)
```

### Test Composite
```bash
# 1. Visit shop → [6] View Inventory
# 2. See hierarchical tree display
# 3. Notice total value is calculated recursively
# (Future enhancement: Add nested containers like pouches)
```

---

## Design Decisions

### Why These Patterns?

**Singleton for Game:**
- Game state needs to be consistent across all systems
- Scanner resource should only exist once
- Makes testing and debugging easier with single entry point

**Decorator for Weapons:**
- Allows unlimited enchantment combinations without new classes
- Players can stack multiple enchantments
- Easy to add new enchantment types (Ice, Lightning, etc.)

**Composite for Inventory:**
- Natural fit for container-based inventory systems
- Supports nested containers (future expansion)
- Operations like "total value" work automatically via recursion

---

Created as a demonstration of design patterns in Java for Software Design and Engineering course.

