// BASE CLASS (parent) - the Drink class from Assignment 1.
// It keeps the 7 attributes that every drink has.
// CoffeeDrink and NonCoffeeDrink inherit everything from this class.
public class Drink implements DrinkPreparation {

    // All attributes are private so other classes cannot change them directly.
    private String drinkID;
    private String drinkName;
    private double price;        // price for a Medium cup
    private String size;         // Small, Medium or Large
    private String temperature;  // Hot or Iced
    private int quantity;
    private boolean available;

    // Constructor
    // It uses the setters so that wrong values are never saved
    // (in Assignment 1 the constructor skipped this check).
    public Drink(String drinkID, String drinkName, double price, String size,
                 String temperature, int quantity, boolean available) {
        this.drinkID = drinkID;
        this.available = available;
        if (!setDrinkName(drinkName)) {
            this.drinkName = "Unknown Drink";
        }
        setPrice(price);
        if (!setSize(size)) {
            this.size = "Medium";
        }
        if (!setTemperature(temperature)) {
            this.temperature = "Iced";
        }
        if (!setQuantity(quantity)) {
            this.quantity = 1;
        }
    }

    // ---------- Getters ----------
    public String getDrinkID() {
        return drinkID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    // ---------- Setters ----------
    // Each setter checks the value first.
    // It returns true if the value was saved, false if it was rejected.

    public boolean setDrinkName(String drinkName) {
        if (drinkName != null && drinkName.length() > 0) {
            this.drinkName = drinkName;
            return true;
        }
        return false;
    }

    public boolean setPrice(double price) {
        if (price >= 0 && price <= 100) {
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean setSize(String size) {
        if (size.equals("Small") || size.equals("Medium") || size.equals("Large")) {
            this.size = size;
            return true;
        }
        return false;
    }

    public boolean setTemperature(String temperature) {
        if (temperature.equals("Hot") || temperature.equals("Iced")) {
            this.temperature = temperature;
            return true;
        }
        return false;
    }

    public boolean setQuantity(int quantity) {
        if (quantity >= 1 && quantity <= 20) {
            this.quantity = quantity;
            return true;
        }
        return false;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // ---------- Other methods ----------

    // Price of one cup. Small is RM1.00 cheaper, Large is RM2.00 more.
    // Child classes override this to add their own extra charges.
    @Override
    public double calculateUnitPrice() {
        double unitPrice = price;
        if (size.equals("Small")) {
            unitPrice = unitPrice - 1.00;
        } else if (size.equals("Large")) {
            unitPrice = unitPrice + 2.00;
        }
        return unitPrice;
    }

    // Price of one cup x quantity (from Assignment 1).
    public double calculateTotalPrice() {
        return calculateUnitPrice() * quantity;
    }

    // A plain drink (e.g. mineral water) is just taken from the fridge.
    @Override
    public void prepareDrink() {
        System.out.println("[COUNTER] Take " + quantity + " x " + drinkName + " from the fridge.");
    }

    public String getDrinkType() {
        return "Other";
    }

    public String getDetails() {
        return drinkName + " (" + size + ", " + temperature + ")";
    }

    // Makes a new copy of this drink, so the customer can change the size
    // and quantity of their own cup without changing the menu.
    public Drink makeCopy() {
        return new Drink(drinkID, drinkName, price, size, temperature, quantity, available);
    }

    // From Assignment 1.
    public void checkAvailability() {
        if (available) {
            System.out.println(drinkName + " is available.");
        } else {
            System.out.println("Sorry, " + drinkName + " is sold out today.");
        }
    }
}
