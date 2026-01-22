package decorator;

/**
 * Decorator Pattern - Concrete Decorator (Lifesteal)
 * 
 * LifestealEnchantment adds a small damage bonus and provides
 * lifesteal capability. This demonstrates that decorators can
 * add entirely new properties, not just modify existing values.
 * 
 * The lifesteal percentage can be used by the combat system
 * to heal the hero when attacking.
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
    
    /**
     * Returns the lifesteal percentage.
     * This is a NEW capability added by this decorator, not present
     * in the base component interface. Combat system can check if
     * the weapon is a LifestealEnchantment and use this value.
     * 
     * @return The percentage of damage healed back to the attacker
     */
    public int getLifestealPercentage() {
        return LIFESTEAL_PERCENTAGE;
    }
}
