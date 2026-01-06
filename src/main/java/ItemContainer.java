import java.util.ArrayList;
import java.util.List;

public class ItemContainer implements Item {
    private final String name;
    private final int capacity;
    private final List<Item> items;
    
    public ItemContainer(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getValue() {
        // Total value of all contained items (recursive)
        return items.stream()
            .mapToInt(Item::getValue)
            .sum();
    }
    
    @Override
    public String getDescription() {
        return name + " (" + items.size() + "/" + capacity + " slots)";
    }
    
    @Override
    public boolean isContainer() {
        return true;
    }
    
    public boolean addItem(Item item) {
        if (items.size() >= capacity) {
            return false;
        }
        items.add(item);
        return true;
    }
    
    public Item removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.remove(index);
        }
        return null;
    }
    
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
    
    public boolean isFull() {
        return items.size() >= capacity;
    }
    
    public void displayContents(int indent) {
        String indentStr = "  ".repeat(indent);
        System.out.println(indentStr + "+ " + getDescription());
        
        for (Item item : items) {
            if (item.isContainer() && item instanceof ItemContainer container) {
                container.displayContents(indent + 1);
            } else {
                System.out.println(indentStr + "  - " + item.getName() + " (" + item.getValue() + "g)");
            }
        }
    }
    
    public int getCapacity() {
        return capacity;
    }
}

