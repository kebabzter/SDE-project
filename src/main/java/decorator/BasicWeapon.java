package decorator;

/**
 * Concrete Component - basic weapon without decorations
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
