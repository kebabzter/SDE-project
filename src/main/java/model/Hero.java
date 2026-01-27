package model;

import decorator.BasicWeapon;
import decorator.WeaponComponent;
import state.HeroState;
import state.NormalState;

/**
 * State pattern – Context.
 * Stores a reference to the current state ({@link HeroState}), delegates all
 * state-specific work to it, and exposes {@link #setState(HeroState)} for
 * state transitions. States hold a backreference to this Context (Hero).
 *
 * @see <a href="https://refactoring.guru/design-patterns/state">State – Refactoring.Guru</a>
 */
public class Hero {
    private final String name;
    private final HeroType type;
    private int health;
    private int maxHealth;
    private int mana;
    private int level;
    private int gold;
    private int baseAttack;
    private int defense;
    private WeaponComponent weapon;
    private ItemContainer inventory;
    
    /** Current state (State pattern). Context delegates to this object. */
    private HeroState currentState;

    public Hero(String playerName, HeroType type) {
        this.name = playerName;
        this.type = type;
        this.maxHealth = 100 + (type.getStrength() * 5);
        this.health = maxHealth;
        this.mana = 50 + (type.getIntelligence() * 5);
        this.level = 1;
        this.gold = 50;
        this.baseAttack = 5 + type.getStrength();
        this.defense = 5 + (type.getStrength() / 2);
        this.weapon = new BasicWeapon("Rusty Sword", 5);
        this.inventory = new ItemContainer("Backpack", 10);
        
        // Initialize with normal state; context link established at creation
        this.currentState = new NormalState(this);
        this.currentState.onEnter();
        
        inventory.addItem(new SimpleItem("Health Potion", 20, "Restores 30 HP"));
    }

    public void displayStats() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          HERO STATS");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Name:         " + name);
        System.out.println("Class:        " + type.getName());
        System.out.println("Level:        " + level);
        System.out.println("Health:       " + health + "/" + maxHealth);
        System.out.println("Mana:         " + mana);
        System.out.println("Attack:       " + getAttack() + " (Base: " + baseAttack + ")");
        System.out.println("Defense:      " + getDefense());
        System.out.println("Gold:         " + gold);
        System.out.println("Weapon:       " + weapon.getDescription());
        System.out.println("\nStatus:       " + currentState.getStateName());
        if (!currentState.getStateDescription().isEmpty()) {
            System.out.println("  " + currentState.getStateDescription());
        }
        System.out.println("\nAttributes:");
        System.out.println("  Strength:     " + type.getStrength());
        System.out.println("  Intelligence: " + type.getIntelligence());
        System.out.println("  Agility:      " + type.getAgility());
        System.out.println("═══════════════════════════════════════\n");
    }
    
    public int getAttack() {
        return currentState.modifyAttack(baseAttack + weapon.getDamage());
    }
    
    public int getDefense() {
        return currentState.modifyDefense(defense);
    }
    
    public void equipWeapon(WeaponComponent newWeapon) {
        this.weapon = newWeapon;
        System.out.println("Equipped: " + newWeapon.getDescription());
    }
    
    public ItemContainer getInventory() {
        return inventory;
    }
    
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        health = Math.max(0, health - actualDamage);
    }
    
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
    
    public void fullHeal() {
        health = maxHealth;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public void addGold(int amount) {
        gold += amount;
    }
    
    public void removeGold(int amount) {
        gold -= amount;
    }
    
    public void increaseAttack(int amount) {
        baseAttack += amount;
    }
    
    public void increaseDefense(int amount) {
        defense += amount;
    }
    
    /**
     * Context exposes setter for state (State pattern).
     * States call this to initiate transitions, e.g. {@code context.setState(new NormalState(context))}.
     */
    public void setState(HeroState newState) {
        if (currentState != null) {
            currentState.onExit();
        }
        this.currentState = newState;
        currentState.setContext(this);
        currentState.onEnter();
    }
    
    public HeroState getCurrentState() {
        return currentState;
    }
    
    public boolean canAct() {
        return currentState.canAct();
    }
    
    // Context delegates to state, state handles transitions
    public void updateState() {
        currentState.handleTurnUpdate();
    }

    /**
     * State-guided transition: delegate to current state. The state decides
     * whether to transition (e.g. NormalState → PoisonedState).
     */
    public void receivePoison() {
        currentState.onReceivePoison();
    }

    /**
     * State-guided transition: delegate to current state. The state decides
     * whether to transition (e.g. NormalState → StunnedState).
     */
    public void receiveStun() {
        currentState.onReceiveStun();
    }

    // Getters
    public String getName() {
        return name;
    }

    public HeroType getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public int getLevel() {
        return level;
    }
    
    public int getGold() {
        return gold;
    }
    
    public int getBaseAttack() {
        return baseAttack;
    }
    
    public WeaponComponent getWeapon() {
        return weapon;
    }
}
