package decorator;

/**
 * Concrete Decorator - adds fire damage
 */
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
