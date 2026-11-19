// DERIVED CLASS (child) of Drink.
// CoffeeDrink IS-A Drink, so it gets all 7 attributes of Drink.
// It adds espresso shots and milk type, which only coffee has.
public class CoffeeDrink extends Drink {

    private int espressoShots;   // 1 to 4
    private String milkType;     // Fresh Milk, Oat Milk or No Milk

    public CoffeeDrink(String drinkID, String drinkName, double price, String temperature,
                       boolean available, int espressoShots, String milkType) {
        // super() sends the common values to the Drink constructor.
        super(drinkID, drinkName, price, "Medium", temperature, 1, available);
        if (!setEspressoShots(espressoShots)) {
            this.espressoShots = 1;
        }
        if (!setMilkType(milkType)) {
            this.milkType = "Fresh Milk";
        }
    }

    public int getEspressoShots() {
        return espressoShots;
    }

    public String getMilkType() {
        return milkType;
    }

    public boolean setEspressoShots(int espressoShots) {
        if (espressoShots >= 1 && espressoShots <= 4) {
            this.espressoShots = espressoShots;
            return true;
        }
        return false;
    }

    public boolean setMilkType(String milkType) {
        if (milkType.equals("Fresh Milk") || milkType.equals("Oat Milk") || milkType.equals("No Milk")) {
            this.milkType = milkType;
            return true;
        }
        return false;
    }

    // Only coffee has caffeine: about 75mg per espresso shot.
    public int getCaffeine() {
        return espressoShots * 75;
    }

    // Coffee price = normal Drink price + RM2.00 per extra shot + RM1.50 for oat milk.
    @Override
    public double calculateUnitPrice() {
        double unitPrice = super.calculateUnitPrice();
        unitPrice = unitPrice + (espressoShots - 1) * 2.00;
        if (milkType.equals("Oat Milk")) {
            unitPrice = unitPrice + 1.50;
        }
        return unitPrice;
    }

    // Coffee is made at the espresso machine.
    @Override
    public void prepareDrink() {
        System.out.print("[ESPRESSO BAR] Pull " + espressoShots + " shot(s) for "
                + getQuantity() + " x " + getDrinkName());
        if (!milkType.equals("No Milk")) {
            System.out.print(", add " + milkType);
        }
        if (getTemperature().equals("Iced")) {
            System.out.println(", pour over ice.");
        } else {
            System.out.println(", serve hot.");
        }
    }

    @Override
    public String getDrinkType() {
        return "Coffee";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + " - " + espressoShots + " shot(s), " + milkType;
    }

    @Override
    public Drink makeCopy() {
        return new CoffeeDrink(getDrinkID(), getDrinkName(), getPrice(), getTemperature(),
                isAvailable(), espressoShots, milkType);
    }
}
