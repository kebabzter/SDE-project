# The Pit - Dungeon Crawler

A text-based dungeon crawler game demonstrating multiple design patterns in Java.

Link to the repo [here](https://github.com/kebabzter/SDE-project)

## Game Overview

**The Pit** is an endless survival dungeon crawler where you:
- Choose a hero class (Goblin, Knight, or Wizard)
- Fight increasingly difficult enemies
- Earn gold to buy upgrades in the shop
- Try to survive as deep as possible

---

## Team Collaboration

This project was developed as a collaborative effort between two team members:

- **alexmanev-code** - Focused on Hero and Enemy systems
  - Implemented **State Pattern** for hero status effects (Normal, Poisoned, Stunned)
  - Implemented **Strategy Pattern** for enemy AI behaviors (Aggressive, Defensive, Smart)
  - Implemented **Factory Method Pattern** for creating varied enemy types with difficulty scaling

- **kebabzter** - Focused on Shop and Item systems
  - Implemented **Decorator Pattern** for weapon enchantments
  - Implemented **Composite Pattern** for hierarchical inventory management
  - Implemented **Singleton Pattern** for centralized game state management

This division of work allowed each team member to specialize in a distinct subsystem while maintaining clean interfaces and demonstrating complementary design patterns. The patterns chosen for each subsystem naturally fit their respective domains, creating a cohesive application that showcases real-world pattern usage.

---

## Design Patterns Implemented

This project demonstrates **6 design patterns** from **3 different categories**:
- **2 Creational Patterns:** Singleton, Factory Method
- **2 Structural Patterns:** Decorator, Composite
- **2 Behavioral Patterns:** State, Strategy

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

## 2. FACTORY METHOD PATTERN (Creational)

### Where It's Used
**Package:** `factory/`
**Files:** `EnemyCreator.java`, `GoblinCreator.java`, `SkeletonCreator.java`, `OrcCreator.java`, `DarkMageCreator.java`, `DemonCreator.java`, `EnemyFactory.java`

### What It Does
Uses an abstract creator class with a factory method that subclasses override to create specific enemy types. Each concrete creator encapsulates the logic for creating its enemy type with appropriate stats and AI strategies.

### How It Works

```java
// Abstract Creator with factory method
public abstract class EnemyCreator {
    /**
     * Factory method – subclasses override to create specific concrete products.
     * Return type matches the product interface (Enemy).
     */
    public abstract Enemy createEnemy(int level);
}

// Concrete Creator for Goblins
public class GoblinCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy(int level) {
        return new Goblin(level);
    }
}

// Factory registry – client for Factory Method pattern
public class EnemyFactory {
    private static final Map<EnemyType, EnemyCreator> creators = new HashMap<>();
    
    static {
        creators.put(EnemyType.GOBLIN, new GoblinCreator());
        creators.put(EnemyType.SKELETON, new SkeletonCreator());
        creators.put(EnemyType.ORC, new OrcCreator());
        creators.put(EnemyType.DARK_MAGE, new DarkMageCreator());
        creators.put(EnemyType.DEMON, new DemonCreator());
    }
    
    // Selects creator type and delegates to concrete creators
    public static Enemy createEnemy(EnemyType type, int level) {
        EnemyCreator creator = creators.get(type);
        return creator.createEnemy(level);
    }
}
```

### Usage in Code

**Game.java (generateRoom method):**
```java
private Room generateRoom(int level) {
    Room room = new Room(level);
    
    int enemyCount = 1 + (level / 3);
    for (int i = 0; i < enemyCount; i++) {
        // Factory Method creates enemies via concrete creators
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
   - Goblin: weak but nimble (multiplier 0.8, Defensive AI)
   - Demon: deadly and strong (multiplier 1.4, Aggressive AI)

### Benefits
 **Polymorphism over Conditionals** - Uses inheritance instead of switch statements  
 **Single Responsibility** - Each creator handles one enemy type  
 **Easy to Extend** - Add new enemy types by creating new creator classes  
 **Encapsulation** - Each creator knows how to create its specific enemy  
 **Open/Closed Principle** - Add new types without modifying existing code  

### Why This Pattern?
Instead of a Simple Factory with switch statements, the Factory Method pattern uses polymorphism. Each concrete creator (GoblinCreator, SkeletonCreator, etc.) overrides the factory method to produce its specific enemy type. This makes the code more maintainable and follows the Open/Closed Principle.

---

## 3. DECORATOR PATTERN (Structural)

### Where It's Used
**Package:** `decorator/`
**Files:** `WeaponComponent.java`, `BasicWeapon.java`, `WeaponDecorator.java`, `FlameEnchantment.java`, `FrostEnchantment.java`, `LifestealEnchantment.java`, `PoisonEnchantment.java`

### What It Does
Dynamically adds enchantment bonuses to weapons without modifying the original weapon class. Multiple enchantments can be stacked on a single weapon.

### How It Works

```java
// Component Interface
public interface WeaponComponent {
    String getName();
    int getDamage();
    String getDescription();
}

// Concrete Component
public class BasicWeapon implements WeaponComponent {
    private final String name;
    private final int damage;
    
    public BasicWeapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getDamage() {
        return damage;
    }
    
    @Override
    public String getDescription() {
        return name + " (+" + damage + " ATK)";
    }
}

// Base Decorator
public abstract class WeaponDecorator implements WeaponComponent {
    protected final WeaponComponent wrappedWeapon;
    
    public WeaponDecorator(WeaponComponent weapon) {
        this.wrappedWeapon = weapon;
    }
    
    @Override
    public String getName() {
        return wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage(); // Delegate to wrapped component
    }
    
    @Override
    public String getDescription() {
        return wrappedWeapon.getDescription();
    }
    
    // Concrete decorators override these
    public abstract String getEnchantmentName();
    public abstract int getEnchantmentBonus();
}

// Concrete Decorator - Flame Enchantment
public class FlameEnchantment extends WeaponDecorator {
    private static final int FLAME_DAMAGE_BONUS = 7;
    private static final String ENCHANTMENT_NAME = "Flame";
    
    public FlameEnchantment(WeaponComponent weapon) {
        super(weapon);
    }
    
    @Override
    public String getName() {
        return ENCHANTMENT_NAME + " " + wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + FLAME_DAMAGE_BONUS;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (+" + getDamage() + " ATK) [" + ENCHANTMENT_NAME + ": +" + FLAME_DAMAGE_BONUS + " fire damage]";
    }
    
    @Override
    public String getEnchantmentName() {
        return ENCHANTMENT_NAME;
    }
    
    @Override
    public int getEnchantmentBonus() {
        return FLAME_DAMAGE_BONUS;
    }
}
```

### Usage in Code

**Hero.java:**
```java
// Hero starts with basic weapon
private WeaponComponent weapon = new BasicWeapon("Rusty Sword", 5); // +5 damage

public int getAttack() {
    return baseAttack + weapon.getDamage();
}
```

**Shop.java (enchantWeapon method):**
```java
// Wrap current weapon with enchantment
WeaponComponent currentWeapon = hero.getWeapon();
FlameEnchantment enchanted = new FlameEnchantment(currentWeapon);
hero.equipWeapon(enchanted);

// Can stack multiple enchantments:
// FrostEnchantment frost = new FrostEnchantment(enchanted);
// Result: "Frost Flame Rusty Sword" with 5 + 7 + 5 = 17 damage
```

### Where to See It
1. Start the game - hero has "Rusty Sword (+5 ATK)"
2. Earn gold and visit shop → Select **[5] Enchant Weapon**
3. Choose from 4 enchantment options:
   - **[1] Flame Enchant** - +7 fire damage
   - **[2] Frost Enchant** - +5 cold damage
   - **[3] Vampiric Enchant** - +3 damage, 15% lifesteal
   - **[4] Toxic Enchant** - +4 poison damage
4. See transformation:
   ```
   Before: Rusty Sword (+5 ATK)
   After:  Flame Rusty Sword (+12 ATK) [Flame: +7 fire damage]
   ```
5. Can enchant multiple times to stack bonuses!

### Benefits
 **Open/Closed Principle** - Add features without modifying Weapon class  
 **Flexible Enhancement** - Add/remove bonuses dynamically at runtime  
 **Stackable** - Can wrap decorators around decorators for multiple enchantments  
 **No Subclass Explosion** - Don't need FlameWeapon, IceWeapon, etc. classes  
 **Base Decorator** - Proper structure with abstract base decorator class  

### Why This Pattern?
Instead of creating subclasses for every weapon variation (FlameRustySword, IceIronSword, etc.), we can dynamically "decorate" any weapon with any enchantment. The base decorator class ensures all decorators follow the same structure, and multiple decorators can be stacked.

---

## 4. STATE PATTERN (Behavioral)

### Where It's Used
**Package:** `state/`
**Files:** `HeroState.java`, `NormalState.java`, `PoisonedState.java`, `StunnedState.java`
**Also:** `model/Hero.java` (Context)

### What It Does
Allows a hero to change behavior dynamically based on status effects (Normal, Poisoned, Stunned). States have access to the context (Hero) and are responsible for triggering their own transitions when conditions are met.

### How It Works

```java
// State Interface
public interface HeroState {
    // States store backreference to context (Hero)
    void setContext(Hero context);
    Hero getContext(); // Used to initiate transitions
    
    void onEnter();
    void onExit();
    
    int modifyAttack(int baseAttack);
    int modifyDefense(int baseDefense);
    boolean canAct();
    
    String getStateName();
    String getStateDescription();
    
    // States manage their own transitions via handleTurnUpdate
    void handleTurnUpdate();
    
    default int getTurnDamage() {
        return 0;
    }
}

// Concrete State: Normal
public class NormalState implements HeroState {
    private Hero context;
    
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public void handleTurnUpdate() {
        // Normal state doesn't transition
    }
}

// Concrete State: Poisoned
public class PoisonedState implements HeroState {
    private Hero context;
    private int turnsRemaining = 3;
    
    private static final int POISON_DURATION = 3;
    private static final int POISON_DAMAGE_PER_TURN = 5;
    private static final double ATTACK_REDUCTION = 0.6;
    
    public PoisonedState(Hero context) {
        this.context = context;
    }
    
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public Hero getContext() {
        return context;
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        return (int)(baseAttack * ATTACK_REDUCTION); // 40% reduction
    }
    
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State triggers own transition when duration expires
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState(context));
        }
    }
    
    @Override
    public int getTurnDamage() {
        return POISON_DAMAGE_PER_TURN; // Poison damage per turn
    }
}
```

### Usage in Code

**Hero.java (Context):**
```java
public class Hero {
    private HeroState currentState;
    
    public void setState(HeroState newState) {
        if (currentState != null) {
            currentState.onExit();
        }
        this.currentState = newState;
        currentState.setContext(this); // Set context reference
        currentState.onEnter();
    }
    
    public int getAttack() {
        // Delegate to state
        return currentState.modifyAttack(baseAttack + weapon.getDamage());
    }
    
    // Context delegates to state, state handles transitions
    public void updateState() {
        currentState.handleTurnUpdate();
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
        
        // Apply state damage (e.g. poison)
        int turnDamage = hero.getCurrentState().getTurnDamage();
        if (turnDamage > 0) {
            hero.takeDamage(turnDamage);
        }
        
        // Update state - state will transition itself if needed
        hero.updateState(); // Delegates to state.handleTurnUpdate()
    }
}
```

### Where to See It
1. Start the game and enter combat
2. Watch for status effect messages:
   ```
   ☠ [Hero] has been POISONED! (lasts 3 turns)
   ⭐ [Hero] has been STUNNED! (lasts 2 turns)
   ✓ The poison wears off!
   ✓ [Hero] returns to normal!
   ```
3. Notice:
   - **Poisoned:** Your attack drops by 40%, and you take 5 damage/turn for 3 turns
   - **Stunned:** You cannot attack for 2 turns, defense is halved
   - **Normal:** Full abilities, no penalties
4. States automatically transition back to Normal when duration expires

### Benefits
 **Encapsulation** - Each state contains its own behavior logic  
 **State-Driven Transitions** - States decide when to transition, not the context  
 **Context Reference** - States have access to context to trigger transitions  
 **Open/Closed Principle** - Add new states without modifying existing ones  
 **Single Responsibility** - Each state class handles one state's behavior  
 **Runtime Behavior Change** - Hero behavior changes dynamically during combat  

### Why This Pattern?
The State pattern elegantly handles status effects by delegating to state objects. States are responsible for managing their own lifecycle and triggering transitions when conditions are met (e.g., duration expires). This follows the Refactoring Guru State pattern structure where states have context access and control their own transitions.

---

## 5. STRATEGY PATTERN (Behavioral)

### Where It's Used
**Package:** `strategy/`
**Files:** `EnemyAI.java`, `AggressiveAI.java`, `DefensiveAI.java`, `SmartAI.java`
**Also:** `model/Enemy.java`, `factory/EnemyCreator.java`, `Game.java`

### What It Does
Encapsulates different enemy AI behaviors into strategies. Each enemy type uses a different strategy to decide whether to attack, defend, or heal during combat.

### How It Works

```java
// Strategy Interface
public interface EnemyAI {
    enum Action {
        ATTACK("Attack"),
        DEFEND("Defend"),
        HEAL("Heal");
        
        private final String displayName;
        
        Action(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Concrete strategies override to implement different behaviors
    Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth);
    String getStrategyName();
    String getDescription();
}

// Concrete Strategy 1: Always attacks
public class AggressiveAI implements EnemyAI {
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        return Action.ATTACK;
    }
    
    @Override
    public String getStrategyName() {
        return "Aggressive";
    }
    
    @Override
    public String getDescription() {
        return "Always attacks - relentless and dangerous";
    }
}

// Concrete Strategy 2: Defends when wounded
public class DefensiveAI implements EnemyAI {
    private static final double DEFEND_THRESHOLD = 0.5;
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double healthPercentage = (double) enemy.getHealth() / enemy.getMaxHealth();
        
        if (healthPercentage <= DEFEND_THRESHOLD) {
            return Action.DEFEND;
        }
        
        return Action.ATTACK;
    }
    
    @Override
    public String getStrategyName() {
        return "Defensive";
    }
    
    @Override
    public String getDescription() {
        return "Defends when wounded - tactically cautious";
    }
}

// Concrete Strategy 3: Intelligent adaptation
public class SmartAI implements EnemyAI {
    private static final double LOW_HEALTH_THRESHOLD = 0.4;
    private static final double WEAK_HERO_THRESHOLD = 0.3;
    
    @Override
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double enemyHealthPercent = (double) enemy.getHealth() / enemy.getMaxHealth();
        double heroHealthPercent = (double) heroHealth / heroMaxHealth;
        
        if (enemyHealthPercent < LOW_HEALTH_THRESHOLD) {
            return Action.DEFEND;
        }
        
        if (heroHealthPercent <= WEAK_HERO_THRESHOLD) {
            return Action.ATTACK;
        }
        
        if (heroHealthPercent > enemyHealthPercent) {
            return Action.ATTACK;
        } else {
            return Action.DEFEND;
        }
    }
    
    @Override
    public String getStrategyName() {
        return "Smart";
    }
    
    @Override
    public String getDescription() {
        return "Adapts tactics based on battle state - dangerous and cunning";
    }
}
```

### Usage in Code

**factory/GoblinCreator.java (assigning strategies):**
```java
public class GoblinCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy(int level) {
        return new Goblin(level); // Goblin constructor initializes with DefensiveAI
    }
}
```

**factory/DemonCreator.java:**
```java
public class DemonCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy(int level) {
        return new Demon(level); // Demon constructor initializes with AggressiveAI
    }
}
```

**Game.java (using strategies in combat):**
```java
// Enemy decides action using AI strategy
EnemyAI.Action enemyAction = enemy.decideAction(hero.getHealth(), hero.getMaxHealth());
performEnemyAction(enemy, hero, enemyAction);

// Performs the chosen action
private void performEnemyAction(Enemy enemy, Hero hero, EnemyAI.Action action) {
    switch (action) {
        case ATTACK:
            int damage = enemy.getAttack();
            hero.takeDamage(damage);
            System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") attacks!");
        case DEFEND:
            System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") braces!");
        case HEAL:
            System.out.println(enemy.getName() + " (" + enemy.getStrategy().getStrategyName() + ") heals!");
    }
}
```

### Where to See It
1. Start the game and fight enemies
2. Watch enemy AI strategies in action:
   ```
   Goblin (Defensive) braces for impact!
   Skeleton (Aggressive) attacks for 8 damage!
   Orc (Smart) braces for impact!
   Dark Mage (Smart) attacks for 12 damage!
   Demon (Aggressive) attacks for 15 damage!
   ```
3. Notice:
   - **Goblins** defend when at half health or lower (Defensive AI)
   - **Skeletons** always attack (Aggressive AI)
   - **Orcs & Dark Mages** adapt based on health levels (Smart AI)
   - **Demons** always attack (Aggressive AI)

### Benefits
 **Easy to Extend** - Add new AI behaviors without modifying Enemy class  
 **Runtime Switching** - Change strategies during runtime  
 **Encapsulation** - Each strategy is independent and focused  
 **Testability** - Strategies can be tested in isolation  
 **Reusability** - Same strategy can be used by different enemy types  

### Why This Pattern?
Without Strategy pattern, Enemy would need massive conditional logic. The Strategy pattern cleanly separates decision logic into reusable, independent classes. Each enemy type gets its strategy assigned by its creator in the Factory Method pattern.

---

## 6. COMPOSITE PATTERN (Structural)

### Where It's Used
**Package:** `model/`
**Files:** `Item.java`, `SimpleItem.java`, `ItemContainer.java`

### What It Does
Creates a tree structure where containers can hold items OR other containers, treating both uniformly.

### How It Works

```java
// Component Interface
public interface Item {
    String getName();
    int getValue();
    String getDescription();
    
    default boolean isContainer() {
        return false;
    }
}

// Leaf - Simple item
public class SimpleItem implements Item {
    private final String name;
    private final int value;
    private final String description;
    
    public SimpleItem(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }
    
    @Override
    public int getValue() {
        return value; // Just return own value
    }
    
    @Override
    public boolean isContainer() {
        return false; // Not a container
    }
}

// Composite - Container that holds Items
public class ItemContainer implements Item {
    private final String name;
    private final int capacity;
    private final List<Item> items;
    
    public ItemContainer(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }
    
    public boolean addItem(Item item) {
        if (items.size() >= capacity) {
            return false;
        }
        items.add(item); // Can add SimpleItem OR ItemContainer
        return true;
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
    
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
```

### Usage in Code

**Hero.java initialization:**
```java
// Initialize hero with backpack inventory
this.inventory = new ItemContainer("Backpack", 10);

// Add a simple item (SimpleItem)
inventory.addItem(new SimpleItem("Health Potion", 20, "Restores 30 HP"));

// Can also add another container inside (Composite can contain Composite)!
ItemContainer pouch = new ItemContainer("Gold Pouch", 5);
inventory.addItem(pouch);

// Now calculate total value – works recursively through entire tree
int totalValue = inventory.getValue(); // 20 + (0, since pouch is empty)
```

**ItemContainer.java (recursive structure):**
```java
public int getValue() {
    // Total value of all contained items (recursive)
    // Works whether item is SimpleItem or ItemContainer
    return items.stream()
        .mapToInt(Item::getValue)
        .sum();
}

public List<Item> getItems() {
    return new ArrayList<>(items);
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
In a real game, you might have nested containers. The Composite pattern lets you treat all of this uniformly with one interface, and operations like "count total value" work recursively through the entire tree.

---

## Pattern Summary Table

| Pattern | Category | Package/Files | Demonstrated In |
|---------|----------|---------------|-----------------|
| **Singleton** | Creational | `Game.java`, `Main.java` | Game instance management |
| **Factory Method** | Creational | `factory/` - EnemyCreator, concrete creators, EnemyFactory | Room generation - Varied enemy types with strategies |
| **Decorator** | Structural | `decorator/` - WeaponComponent, BasicWeapon, WeaponDecorator, enchantments | Shop option [5] - Multiple weapon enchantments |
| **State** | Behavioral | `state/` - HeroState, NormalState, PoisonedState, StunnedState, `model/Hero.java` | Combat - Hero status effects with state-driven transitions |
| **Strategy** | Behavioral | `strategy/` - EnemyAI, AggressiveAI, DefensiveAI, SmartAI | Combat - Enemy AI decision making |
| **Composite** | Structural | `model/` - Item, SimpleItem, ItemContainer | Shop option [6] - Inventory system |

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
mvn exec:java -Dexec.mainClass="Main"
```

### Quick Start
1. **Character Creation:** Choose your hero class (Wizard, Goblin, or Knight)
2. **Combat:** Fight enemies that appear in each room
   - Choose [1] Attack or [2] Defend each turn
3. **Shop:** After clearing a room, visit the shop to:
   - Buy health potions and stat upgrades
   - **[5] Enchant Weapon** - Choose from 4 enchantment types! See Decorator pattern in action!
   - **[6] View Inventory** - See Composite pattern in action!
4. **Survive:** Each level gets harder - see how deep you can go!

---

## Project Structure

```
src/main/java/
├── Main.java              # Entry point (uses Singleton)
├── Game.java              # SINGLETON - game state manager
├── Shop.java              # Shop system (uses Decorator)
│
├── factory/               # Factory Method Pattern
│   ├── EnemyCreator.java      # Abstract Creator
│   ├── GoblinCreator.java     # Concrete Creator
│   ├── SkeletonCreator.java   # Concrete Creator
│   ├── OrcCreator.java        # Concrete Creator
│   ├── DarkMageCreator.java   # Concrete Creator
│   ├── DemonCreator.java      # Concrete Creator
│   └── EnemyFactory.java      # Creator registry
│
├── decorator/             # Decorator Pattern
│   ├── WeaponComponent.java   # Component Interface
│   ├── BasicWeapon.java       # Concrete Component
│   ├── WeaponDecorator.java   # Base Decorator
│   ├── FlameEnchantment.java  # Concrete Decorator
│   ├── FrostEnchantment.java  # Concrete Decorator
│   ├── LifestealEnchantment.java  # Concrete Decorator
│   └── PoisonEnchantment.java # Concrete Decorator
│
├── state/                 # State Pattern
│   ├── HeroState.java         # State Interface
│   ├── NormalState.java       # Concrete State
│   ├── PoisonedState.java     # Concrete State
│   └── StunnedState.java      # Concrete State
│
├── strategy/              # Strategy Pattern
│   ├── EnemyAI.java           # Strategy Interface
│   ├── AggressiveAI.java      # Concrete Strategy
│   ├── DefensiveAI.java       # Concrete Strategy
│   └── SmartAI.java           # Concrete Strategy
│
└── model/                 # Domain Models
    ├── Hero.java              # Player character (State Context)
    ├── HeroType.java          # Hero class definitions
    ├── Enemy.java             # Enemy entities (Strategy Context)
    ├── Room.java              # Room/level container
    ├── Item.java              # COMPOSITE - component interface
    ├── SimpleItem.java        # COMPOSITE - leaf node
    └── ItemContainer.java     # COMPOSITE - composite node
```

---

# Summary

## Pattern Implementations

#### ✅ Factory Method Pattern
Encapsulates enemy creation with polymorphic constructors. `EnemyCreator` is the abstract creator with `createEnemy()` factory method. Concrete creators (`GoblinCreator`, `SkeletonCreator`, `OrcCreator`, `DarkMageCreator`, `DemonCreator`) extend it and instantiate their respective `Enemy` products. `EnemyFactory` coordinates creation requests and manages the registry of available creators. Complies with Refactoring.Guru standard.

#### ✅ State Pattern
Manages hero status effects through state objects (`NormalState`, `PoisonedState`, `StunnedState`). `Hero` is the context storing a `HeroState` reference and delegating behavior methods (`getAttack()`, `getDefense()`, `canAct()`) to the current state. States store backreferences to the hero and trigger their own transitions when durations expire. Complies with Refactoring.Guru standard.

#### ✅ Decorator Pattern
Dynamically adds weapon enchantments without modifying the weapon class. `WeaponComponent` is the component interface. `BasicWeapon` is the concrete component. `WeaponDecorator` is the abstract decorator wrapping a `WeaponComponent`. Concrete decorators (`FlameEnchantment`, `FrostEnchantment`, `LifestealEnchantment`, `PoisonEnchantment`) extend it and add damage bonuses. Multiple decorators can be stacked. Complies with Refactoring.Guru standard.

#### ✅ Strategy Pattern
Encapsulates different enemy AI behaviors in strategy objects. `EnemyAI` is the strategy interface with `selectAction()` method returning an `Action` (ATTACK, DEFEND, HEAL). Concrete strategies (`AggressiveAI`, `DefensiveAI`, `SmartAI`) implement different decision-making algorithms. Strategies are assigned during enemy creation via the Factory Method pattern. Complies with Refactoring.Guru standard.

#### ✅ Composite Pattern
Structures the inventory as a hierarchical tree of items. `Item` is the component interface. `SimpleItem` is the leaf node (no children). `ItemContainer` is the composite node containing a list of items. Both are accessed uniformly via the `Item` interface. `getValue()` recursively sums all contained items. Complies with Refactoring.Guru standard.

#### ✅ Singleton Pattern
Ensures only one game instance manages all game state. `Game` has a private constructor, static `instance` variable, and public `getInstance()` method with double-checked locking for thread safety. Lazy initialization creates the instance on first access. Complies with Refactoring.Guru standard.

---

The project includes comprehensive unit tests using **JUnit 5** to verify the design pattern implementations.

### Run Tests
```bash
# Run all tests
mvn test

# Run Factory Method tests
mvn test -Dtest=factory.FactoryMethodPatternTest

# Run State Pattern tests
mvn test -Dtest=state.StatePatternTest
```

**Test Coverage:**
- **Factory Method:** 32 tests covering product structure, creators, factories, and behavior
- **State Pattern:** 20 tests covering context, states, transitions, and behavior
- **Total:** 52 automated unit tests (JUnit 5)

Test results available in `target/surefire-reports/` directory.

---

## Design Decisions

### Why These Patterns?

**Singleton for Game:**
- Game state needs to be consistent across all systems
- Scanner resource should only exist once
- Makes testing and debugging easier with single entry point

**Factory Method for Enemies:**
- Uses polymorphism instead of switch statements
- Each concrete creator encapsulates enemy-specific logic
- Easy to add new enemy types - just create a new creator class
- Each enemy type automatically gets its appropriate AI strategy
- Follows Open/Closed Principle

**Decorator for Weapons:**
- Allows unlimited enchantment combinations without new classes
- Players can stack multiple enchantments
- Easy to add new enchantment types (Ice, Lightning, etc.)
- Proper structure with base decorator class

**State for Hero Status Effects:**
- Cleanly separates behavior for each status effect (Normal, Poisoned, Stunned)
- States have context reference and trigger their own transitions
- Eliminates massive conditional logic that would clutter the Hero class
- Makes it easy to add new status effects (Burning, Frozen, Blessed, etc.)
- Each state is self-contained and can be tested independently

**Strategy for Enemy AI:**
- Different enemy types have distinct personalities and behaviors
- AI logic is separated from Enemy class, following Single Responsibility Principle
- Easy to add new AI strategies (Tactical, Cowardly, Berserker, etc.)
- Strategies can be swapped at runtime
- Makes combat more interesting and varied - players face different challenges

**Composite for Inventory:**
- Natural fit for container-based inventory systems
- Supports nested containers (future expansion)
- Operations like "total value" work automatically via recursion

---

Created as a demonstration of design patterns in Java for Software Design and Engineering course.
