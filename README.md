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
    public abstract Enemy createEnemy(int level);
    public abstract String getEnemyTypeName();
    public abstract double getDifficultyMultiplier();
    
    protected Enemy createConfiguredEnemy(int level, EnemyAI strategy) {
        // Common creation logic
        int health = calculateScaledStat(BASE_HEALTH, level, 10);
        int attack = calculateScaledStat(BASE_ATTACK, level, 2);
        // ... create and configure enemy
        Enemy enemy = new Enemy(getEnemyTypeName(), health, attack, defense, gold);
        enemy.setStrategy(strategy);
        return enemy;
    }
}

// Concrete Creator for Goblins
public class GoblinCreator extends EnemyCreator {
    private static final double DIFFICULTY_MULTIPLIER = 0.8;
    
    @Override
    public Enemy createEnemy(int level) {
        return createConfiguredEnemy(level, new DefensiveAI());
    }
    
    @Override
    public String getEnemyTypeName() {
        return "Goblin";
    }
}

// Factory registry
public class EnemyFactory {
    private static final Map<EnemyType, EnemyCreator> creators = new HashMap<>();
    
    static {
        creators.put(EnemyType.GOBLIN, new GoblinCreator());
        creators.put(EnemyType.SKELETON, new SkeletonCreator());
        // ... other creators
    }
    
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
    // ... implementation
}

// Base Decorator
public abstract class WeaponDecorator implements WeaponComponent {
    protected final WeaponComponent wrappedWeapon;
    
    public WeaponDecorator(WeaponComponent weapon) {
        this.wrappedWeapon = weapon;
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage(); // Delegate to wrapped component
    }
}

// Concrete Decorator - Flame Enchantment
public class FlameEnchantment extends WeaponDecorator {
    private static final int FLAME_DAMAGE_BONUS = 7;
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + FLAME_DAMAGE_BONUS;
    }
    
    @Override
    public String getName() {
        return "Flame " + wrappedWeapon.getName();
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
    void setContext(Hero context); // States have context reference
    void onEnter();
    void onExit();
    int modifyAttack(int baseAttack);
    int modifyDefense(int baseDefense);
    boolean canAct();
    String getStateName();
    String getStateDescription();
    void handleTurnUpdate(); // States manage their own transitions
    int getTurnDamage();
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
    
    @Override
    public void setContext(Hero context) {
        this.context = context;
    }
    
    @Override
    public int modifyAttack(int baseAttack) {
        return (int)(baseAttack * 0.6); // 40% reduction
    }
    
    @Override
    public void handleTurnUpdate() {
        turnsRemaining--;
        
        // State triggers its own transition when duration expires
        if (turnsRemaining <= 0 && context != null) {
            context.setState(new NormalState());
        }
    }
    
    @Override
    public int getTurnDamage() {
        return 5; // Poison damage per turn
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
    }
    
    Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth);
    String getStrategyName();
    String getDescription();
}

// Concrete Strategy 1: Always attacks
public class AggressiveAI implements EnemyAI {
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        return Action.ATTACK;
    }
}

// Concrete Strategy 2: Defends when wounded
public class DefensiveAI implements EnemyAI {
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double healthPercent = (double) enemy.getHealth() / enemy.getMaxHealth();
        return (healthPercent <= 0.5) ? Action.DEFEND : Action.ATTACK;
    }
}

// Concrete Strategy 3: Intelligent adaptation
public class SmartAI implements EnemyAI {
    public Action selectAction(Enemy enemy, int heroHealth, int heroMaxHealth) {
        double enemyHP = (double) enemy.getHealth() / enemy.getMaxHealth();
        double heroHP = (double) heroHealth / heroMaxHealth;
        
        if (enemyHP < 0.4) return Action.DEFEND;
        if (heroHP <= 0.3) return Action.ATTACK;
        return heroHP > enemyHP ? Action.ATTACK : Action.DEFEND;
    }
}
```

### Usage in Code

**factory/GoblinCreator.java (assigning strategies):**
```java
public class GoblinCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy(int level) {
        return createConfiguredEnemy(level, new DefensiveAI()); // Goblins use defensive AI
    }
}
```

**factory/DemonCreator.java:**
```java
public class DemonCreator extends EnemyCreator {
    @Override
    public Enemy createEnemy(int level) {
        return createConfiguredEnemy(level, new AggressiveAI()); // Demons use aggressive AI
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

// Composite - Container that holds Items
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

## Testing the Patterns

### Test Singleton
```bash
# Run the game and check Main.java
# Notice it uses Game.getInstance() - only one instance created
```

### Test Factory Method
```bash
# 1. Start the game
# 2. Enter rooms at different levels
# 3. Observe different enemy types appearing:
#    - Level 1: Goblin (weak, 0.8x multiplier, Defensive AI)
#    - Level 2: Skeleton (standard, 1.0x multiplier, Aggressive AI)
#    - Level 3: Orc (tougher, 1.2x multiplier, Smart AI)
#    - Level 4: Dark Mage (threatening, 1.1x multiplier, Smart AI)
#    - Level 5+: Demon (deadly, 1.4x multiplier, Aggressive AI)
# 4. Notice multi-enemy rooms have varied types
# 5. Check enemy stats scale based on level and type
# 6. Each enemy type uses its assigned AI strategy
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
# 4. Watch effects expire automatically:
#    ✓ The poison wears off!
#    ✓ [Hero] returns to normal!
# 5. States transition themselves when duration expires
```

### Test Strategy
```bash
# 1. Start game and fight enemies at different levels
# 2. Observe different enemy AI behaviors:
#    Goblin (Defensive) braces for impact!      # Defensive
#    Skeleton (Aggressive) attacks for damage!  # Aggressive
#    Orc (Smart) attacks strategically!         # Smart/Adaptive
#    Dark Mage (Smart) braces for impact!       # Smart/Adaptive
#    Demon (Aggressive) attacks for damage!     # Aggressive
# 3. Notice:
#    - Goblins defend when at half health or lower
#    - Skeletons always attack relentlessly
#    - Orcs and Dark Mages switch tactics based on health
#    - Demons always press the attack
# 4. Try fighting same enemy type at different health levels
# 5. See AI behavior change based on battle state
```

### Test Decorator
```bash
# 1. Start game
# 2. Fight enemies until you have 60+ gold
# 3. Visit shop → [5] Enchant Weapon
# 4. Choose from 4 enchantment options:
#    - Flame Enchant (+7 fire damage)
#    - Frost Enchant (+5 cold damage)
#    - Vampiric Enchant (+3 damage, 15% lifesteal)
#    - Toxic Enchant (+4 poison damage)
# 5. Check hero stats - weapon now has enchantment prefix and bonus
# 6. Can enchant multiple times to stack bonuses!
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
