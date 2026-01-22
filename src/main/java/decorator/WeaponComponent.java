package decorator;

/**
 * Decorator Pattern - Component Interface
 * 
 * This interface defines the common operations for both concrete components
 * and decorators. Both BasicWeapon (concrete component) and WeaponDecorator
 * (base decorator) implement this interface.
 * 
 * PATTERN EXPLANATION (Decorator):
 * The Decorator pattern attaches additional responsibilities to an object
 * dynamically. Decorators provide a flexible alternative to subclassing
 * for extending functionality.
 * 
 * Structure (matching Refactoring Guru):
 * - WeaponComponent: Component interface (this class)
 * - BasicWeapon: Concrete Component
 * - WeaponDecorator: Base Decorator (abstract, wraps component)
 * - FlameEnchantment, FrostEnchantment, etc.: Concrete Decorators
 * 
 * Key principles:
 * 1. Decorators have the same interface as the objects they decorate
 * 2. Decorators wrap objects and delegate to them
 * 3. Multiple decorators can be stacked (e.g., Flame(Frost(BasicWeapon)))
 * 
 * @see <a href="https://refactoring.guru/design-patterns/decorator">Refactoring Guru - Decorator</a>
 */
public interface WeaponComponent {
    
    /**
     * Gets the name of the weapon (may be modified by decorators)
     * @return The weapon name
     */
    String getName();
    
    /**
     * Gets the total damage of the weapon (accumulated through decorators)
     * @return The weapon damage
     */
    int getDamage();
    
    /**
     * Gets a full description of the weapon including all enchantments
     * @return The weapon description
     */
    String getDescription();
}
