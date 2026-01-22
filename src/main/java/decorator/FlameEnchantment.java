package decorator;

/**
 * Decorator Pattern - Concrete Decorator (Flame)
 * 
 * FlameEnchantment adds fire damage bonus to a weapon.
 * This concrete decorator extends WeaponDecorator and adds
 * its specific behavior while delegating to the wrapped component.
 * 
 * Example of stacking decorators:
 * WeaponComponent sword = new BasicWeapon("Sword", 10);
 * sword = new FlameEnchantment(sword);  // Now does 17 damage
 * sword = new FrostEnchantment(sword);  // Now does 22 damage
 */
public class FlameEnchantment extends WeaponDecorator {
    
    private static final int FLAME_DAMAGE_BONUS = 7;
    private static final String ENCHANTMENT_NAME = "Flame";
    
    public FlameEnchantment(WeaponComponent weapon) {
        super(weapon);
    }
    
    /**
     * Prepends "Flame" to the weapon name.
     * Delegates to wrapped component first, then adds prefix.
     */
    @Override
    public String getName() {
        return ENCHANTMENT_NAME + " " + wrappedWeapon.getName();
    }
    
    /**
     * Adds flame damage bonus to the wrapped weapon's damage.
     * This demonstrates the decorator adding behavior: it calls the
     * wrapped component's method and adds its own contribution.
     */
    @Override
    public int getDamage() {
        // Decorator adds its bonus to whatever the wrapped component returns
        // If wrapped is another decorator, this creates a chain of bonuses
        return wrappedWeapon.getDamage() + FLAME_DAMAGE_BONUS;
    }
    
    /**
     * Builds description showing all enchantments.
     */
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
