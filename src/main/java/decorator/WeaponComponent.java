package decorator;

/**
 * Decorator Pattern - Component Interface
 * 
 * Both concrete weapons and decorators implement this interface.
 */
public interface WeaponComponent {
    String getName();
    int getDamage();
    String getDescription();
}
