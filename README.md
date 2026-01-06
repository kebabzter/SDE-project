# The Pit - Dungeon Crawler

A text-based dungeon crawler game demonstrating multiple design patterns in Java.

## Game Overview

**The Pit** is an endless survival dungeon crawler where you:
- Choose a hero class (Warrior, Mage, or Rogue)
- Fight increasingly difficult enemies
- Earn gold to buy upgrades in the shop
- Try to survive as deep as possible

---

## Design Patterns Implemented

This project demonstrates **3 design patterns** from **2 different categories**:
- **1 Creational Pattern:** Singleton
- **2 Structural Patterns:** Decorator, Composite

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
✅ **Single Source of Truth** - All game state in one place  
✅ **Global Access** - Any part of code can access the game  
✅ **Resource Control** - Prevents multiple Scanner instances  
✅ **State Consistency** - No conflicting game states  

### Why This Pattern?
Without Singleton, multiple Game objects could exist with different states, causing bugs and confusion. The Singleton pattern guarantees one coordinated game instance.

---

## 2. DECORATOR PATTERN (Structural)

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
✅ **Open/Closed Principle** - Add features without modifying Weapon class  
✅ **Flexible Enhancement** - Add/remove bonuses dynamically at runtime  
✅ **Stackable** - Can wrap decorators around decorators for multiple enchantments  
✅ **No Subclass Explosion** - Don't need FlameWeapon, IceWeapon, etc. classes  

### Why This Pattern?
Instead of creating subclasses for every weapon variation (FlameRustySword, IceIronSword, etc.), we can dynamically "decorate" any weapon with any enchantment. This keeps code maintainable and flexible.

---

## 3. COMPOSITE PATTERN (Structural)

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
✅ **Uniform Treatment** - Same interface for items and containers  
✅ **Natural Hierarchy** - Backpack → Pouch → Coin Purse → Items  
✅ **Recursive Operations** - Calculate total value, display all, search all  
✅ **Flexible Structure** - Add/remove containers and items freely  

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
| **Decorator** | Structural | `Weapon.java`, `EnchantedWeapon.java` | ~60 | Shop option [5] - Weapon enchanting |
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
├── Game.java              # SINGLETON - game state manager
├── Hero.java              # Player character
├── HeroType.java          # Hero class definitions
├── Enemy.java             # Enemy entities
├── Room.java              # Room/level container
├── Shop.java              # Shop system
│
├── Weapon.java            # DECORATOR - base weapon
├── EnchantedWeapon.java   # DECORATOR - weapon decorator
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

## Future Enhancements

To add more patterns:
- **Factory Pattern** - Create different enemy types
- **Strategy Pattern** - Different AI behaviors for enemies
- **Command Pattern** - Undo combat moves
- **State Pattern** - Hero status effects (poisoned, stunned)
- **Observer Pattern** - Achievement system

---

## License

MIT License - See LICENSE file

---

## Author

Created as a demonstration of design patterns in Java for Software Design and Engineering course.

