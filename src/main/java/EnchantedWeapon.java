public class EnchantedWeapon extends Weapon {
    private final Weapon wrappedWeapon;
    private final String enchantmentName;
    private final int enchantmentBonus;
    
    public EnchantedWeapon(Weapon weapon, String enchantmentName, int bonus) {
        super(weapon.getName(), weapon.getDamage());
        this.wrappedWeapon = weapon;
        this.enchantmentName = enchantmentName;
        this.enchantmentBonus = bonus;
    }
    
    @Override
    public String getName() {
        return enchantmentName + " " + wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage() + enchantmentBonus;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (+" + getDamage() + " ATK) [Enchanted: +" + enchantmentBonus + "]";
    }
}

