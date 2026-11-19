// DERIVED CLASS (child) of Drink.
// NonCoffeeDrink IS-A Drink (e.g. Chocolate, Matcha Latte, Teh Tarik).
// It adds sweetness level and topping, which coffee does not have.
public class NonCoffeeDrink extends Drink {

    private int sweetnessLevel;  // 0, 25, 50, 75 or 100 (%)
    private String topping;      // None, Pearls or Cream

    public NonCoffeeDrink(String drinkID, String drinkName, double price, String temperature,
                          boolean available, int sweetnessLevel, String topping) {
        super(drinkID, drinkName, price, "Medium", temperature, 1, available);
        if (!setSweetnessLevel(sweetnessLevel)) {
            this.sweetnessLevel = 50;
        }
        if (!setTopping(topping)) {
            this.topping = "None";
        }
    }

    public int getSweetnessLevel() {
        return sweetnessLevel;
    }

    public String getTopping() {
        return topping;
    }

    public boolean setSweetnessLevel(int sweetnessLevel) {
        if (sweetnessLevel >= 0 && sweetnessLevel <= 100 && sweetnessLevel % 25 == 0) {
            this.sweetnessLevel = sweetnessLevel;
            return true;
        }
        return false;
    }

    public boolean setTopping(String topping) {
        if (topping.equals("None") || topping.equals("Pearls") || topping.equals("Cream")) {
            this.topping = topping;
            return true;
        }
        return false;
    }

    // Only this type of drink can be sugar-free.
    public boolean isSugarFree() {
        return sweetnessLevel == 0;
    }

    // Price = normal Drink price + RM1.50 if a topping is added.
    @Override
    public double calculateUnitPrice() {
        double unitPrice = super.calculateUnitPrice();
        if (!topping.equals("None")) {
            unitPrice = unitPrice + 1.50;
        }
        return unitPrice;
    }

    // Non-coffee drinks are mixed at the drink station.
    @Override
    public void prepareDrink() {
        System.out.print("[DRINK STATION] Mix " + getQuantity() + " x " + getDrinkName()
                + " with " + sweetnessLevel + "% sugar");
        if (!topping.equals("None")) {
            System.out.print(", add " + topping);
        }
        if (getTemperature().equals("Iced")) {
            System.out.println(", pour over ice.");
        } else {
            System.out.println(", serve hot.");
        }
    }

    @Override
    public String getDrinkType() {
        return "Non-Coffee";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - " + sweetnessLevel + "% sugar, topping: " + topping;
    }

    @Override
    public Drink makeCopy() {
        return new NonCoffeeDrink(getDrinkID(), getDrinkName(), getPrice(), getTemperature(),
                isAvailable(), sweetnessLevel, topping);
    }
}
