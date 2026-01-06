/**
 * Base interface for items.
 */
public interface Item {
    String getName();
    int getValue();
    String getDescription();
    
    default boolean isContainer() {
        return false;
    }
}

