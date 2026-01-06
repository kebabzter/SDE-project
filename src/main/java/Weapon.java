public class Weapon {
    protected String name;
    protected int damage;
    
    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public String getDescription() {
        return name + " (+" + damage + " ATK)";
    }
}

