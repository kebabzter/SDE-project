package model;

public class SimpleItem implements Item {
    private final String name;
    private final int value;
    private final String description;
    
    public SimpleItem(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getValue() {
        return value;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean isContainer() {
        return false;
    }
}
