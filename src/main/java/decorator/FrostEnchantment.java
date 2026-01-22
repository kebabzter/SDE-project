package decorator;

/**
 * Concrete Decorator - adds cold damage
 */
public class FrostEnchantment extends WeaponDecorator {
    
    private static final int FROST_DAMAGE_BONUS = 5;
    private static final String ENCHANTMENT_NAME = "Frost";
    
    public FrostEnchantment(WeaponComponent weapon) {
        super(weapon);
    }
    
    @Override
    public String getName() {
        return ENCHANTMENT_NAME + " " + wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + FROST_DAMAGE_BONUS;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (+" + getDamage() + " ATK) [" + ENCHANTMENT_NAME + ": +" + FROST_DAMAGE_BONUS + " cold damage]";
    }
    
    @Override
    public String getEnchantmentName() {
        return ENCHANTMENT_NAME;
    }
    
    @Override
    public int getEnchantmentBonus() {
        return FROST_DAMAGE_BONUS;
    }
}
