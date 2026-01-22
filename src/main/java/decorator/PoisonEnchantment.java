package decorator;

/**
 * Concrete Decorator - adds poison damage
 */
public class PoisonEnchantment extends WeaponDecorator {
    
    private static final int POISON_DAMAGE_BONUS = 4;
    private static final String ENCHANTMENT_NAME = "Toxic";
    
    public PoisonEnchantment(WeaponComponent weapon) {
        super(weapon);
    }
    
    @Override
    public String getName() {
        return ENCHANTMENT_NAME + " " + wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + POISON_DAMAGE_BONUS;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (+" + getDamage() + " ATK) [" + ENCHANTMENT_NAME + ": +" + POISON_DAMAGE_BONUS + " poison damage]";
    }
    
    @Override
    public String getEnchantmentName() {
        return ENCHANTMENT_NAME;
    }
    
    @Override
    public int getEnchantmentBonus() {
        return POISON_DAMAGE_BONUS;
    }
}
