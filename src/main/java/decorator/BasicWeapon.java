package decorator;

/**
 * Decorator Pattern - Concrete Component
 * 
 * BasicWeapon is the concrete component that provides base functionality.
 * It implements the WeaponComponent interface and can be decorated with
 * various enchantments (decorators) to add additional capabilities.
 * 
 * In the Decorator pattern, this is the object that decorators wrap.
 * It provides the core behavior that decorators extend.
 */
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
