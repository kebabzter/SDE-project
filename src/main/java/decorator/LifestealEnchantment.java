package decorator;

/**
 * Concrete Decorator - adds lifesteal
 */
public class LifestealEnchantment extends WeaponDecorator {
    
    private static final int LIFESTEAL_DAMAGE_BONUS = 3;
    private static final int LIFESTEAL_PERCENTAGE = 15;
    private static final String ENCHANTMENT_NAME = "Vampiric";
    
    public LifestealEnchantment(WeaponComponent weapon) {
        super(weapon);
    }
    
    @Override
    public String getName() {
        return ENCHANTMENT_NAME + " " + wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + LIFESTEAL_DAMAGE_BONUS;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (+" + getDamage() + " ATK) [" + ENCHANTMENT_NAME + ": +" + LIFESTEAL_DAMAGE_BONUS + " damage, " + LIFESTEAL_PERCENTAGE + "% lifesteal]";
    }
    
    @Override
    public String getEnchantmentName() {
        return ENCHANTMENT_NAME;
    }
    
    @Override
    public int getEnchantmentBonus() {
        return LIFESTEAL_DAMAGE_BONUS;
    }
    
    public int getLifestealPercentage() {
        return LIFESTEAL_PERCENTAGE;
    }
}
