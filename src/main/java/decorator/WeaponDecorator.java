package decorator;

/**
 * Decorator Pattern - Base Decorator (Abstract)
 * 
 * This abstract class is the base decorator that implements the same interface
 * as the component it decorates. It holds a reference to a wrapped component
 * and delegates all operations to it.
 * 
 * CRITICAL FOR PATTERN CORRECTNESS:
 * - The base decorator MUST implement the same interface as the component
 * - It MUST wrap a component (composition over inheritance)
 * - It MUST delegate to the wrapped component by default
 * - Concrete decorators extend this and add/modify behavior
 * 
 * According to Refactoring Guru, the base decorator:
 * 1. Defines the wrapping interface (stores reference to wrapped object)
 * 2. Delegates all work to the wrapped object
 * 3. Serves as base class for concrete decorators
 * 
 * This allows decorators to be stacked: Flame(Frost(Lifesteal(BasicWeapon)))
 */
public abstract class WeaponDecorator implements WeaponComponent {
    
    // Reference to the wrapped component (can be BasicWeapon or another decorator)
    // This is the key to the Decorator pattern: decorators wrap components
    protected final WeaponComponent wrappedWeapon;
    
    /**
     * Constructor requires a component to wrap.
     * This enforces that decorators always wrap something.
     * 
     * @param weapon The weapon component to wrap (can be basic or decorated)
     */
    public WeaponDecorator(WeaponComponent weapon) {
        this.wrappedWeapon = weapon;
    }
    
    /**
     * Default delegation to wrapped component.
     * Concrete decorators may override to modify the name.
     */
    @Override
    public String getName() {
        return wrappedWeapon.getName();
    }
    
    /**
     * Default delegation to wrapped component.
     * Concrete decorators typically override this to add damage bonuses.
     */
    @Override
    public int getDamage() {
        return wrappedWeapon.getDamage();
    }
    
    /**
     * Default delegation to wrapped component.
     * Concrete decorators typically override this to add enchantment info.
     */
    @Override
    public String getDescription() {
        return wrappedWeapon.getDescription();
    }
    
    /**
     * Returns the name of this enchantment type.
     * Used in descriptions and for display purposes.
     * 
     * @return The enchantment name
     */
    public abstract String getEnchantmentName();
    
    /**
     * Returns the bonus provided by this specific enchantment.
     * 
     * @return The enchantment bonus value
     */
    public abstract int getEnchantmentBonus();
}
