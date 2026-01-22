package decorator;

/**
 * Decorator Pattern - Base Decorator
 * 
 * Wraps a WeaponComponent and delegates to it.
 * Concrete decorators extend this to add their own behavior.
 */
public abstract class WeaponDecorator implements WeaponComponent {
    
    protected final WeaponComponent wrappedWeapon;
    
    public WeaponDecorator(WeaponComponent weapon) {
        this.wrappedWeapon = weapon;
    }
    
    @Override
    public String getName() {
        return wrappedWeapon.getName();
    }
    
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage();
    }
    
    @Override
    public String getDescription() {
        return wrappedWeapon.getDescription();
    }
    
    public abstract String getEnchantmentName();
    public abstract int getEnchantmentBonus();
}
