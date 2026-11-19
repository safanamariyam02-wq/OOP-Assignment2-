// ADDITIONAL CLASS 2 - one customer's order.
// It stores the drinks in a Drink[] array. The array can hold CoffeeDrink,
// NonCoffeeDrink and Drink objects together, because they are all Drinks.
public class Order {

    private String orderID;
    private Customer customer;
    private String orderType;            // Dine-in or Takeaway
    private Drink[] items = new Drink[10];
    private int count = 0;

    public Order(String orderID, Customer customer, String orderType) {
        this.orderID = orderID;
        this.customer = customer;
        if (orderType.equals("Takeaway")) {
            this.orderType = "Takeaway";
        } else {
            this.orderType = "Dine-in";
        }
    }

    // ---------- Getters ----------
    public String getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getOrderType() {
        return orderType;
    }

    public int getCount() {
        return count;
    }

    public Drink getDrink(int index) {
        if (index >= 0 && index < count) {
            return items[index];
        }
        return null;
    }

    // ---------- Adding and removing drinks ----------

    public boolean addDrink(Drink drink) {
        if (count < items.length && drink.isAvailable()) {
            items[count] = drink;
            count++;
            return true;
        }
        return false;
    }

    // position starts from 1 (what the customer sees on screen).
    public boolean removeDrink(int position) {
        if (position < 1 || position > count) {
            return false;
        }
        // Move every drink after the removed one, one step to the left.
        for (int i = position - 1; i < count - 1; i++) {
            items[i] = items[i + 1];
        }
        items[count - 1] = null;
        count--;
        return true;
    }

    // ---------- Calculations ----------

    // Polymorphism: calculateTotalPrice() uses the correct calculateUnitPrice()
    // for each drink (Coffee, Non-Coffee or plain Drink).
    public double calculateSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < count; i++) {
            subtotal = subtotal + items[i].calculateTotalPrice();
        }
        return subtotal;
    }

    public int getTotalCups() {
        int cups = 0;
        for (int i = 0; i < count; i++) {
            cups = cups + items[i].getQuantity();
        }
        return cups;
    }

    // The order asks the Customer object for the discount.
    public double calculateDiscount() {
        return customer.calculateDiscount(calculateSubtotal());
    }

    // Takeaway cups cost RM0.50 each.
    public double calculateTakeawayFee() {
        if (orderType.equals("Takeaway")) {
            return getTotalCups() * 0.50;
        }
        return 0;
    }

    public double calculateTotal() {
        double total = calculateSubtotal() - calculateDiscount() + calculateTakeawayFee();
        return Math.round(total * 100) / 100.0;   // round to 2 decimal places
    }

    // ---------- Display ----------

    public void showOrder() {
        System.out.println("\n--- Order " + orderID + " (" + orderType + ") ---");
        if (count == 0) {
            System.out.println("Your order is empty.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.printf("%d. %d x %-50s RM%6.2f%n", i + 1, items[i].getQuantity(),
                    items[i].getDetails(), items[i].calculateTotalPrice());
        }
        System.out.printf("Subtotal: RM%.2f%n", calculateSubtotal());
    }

    // Polymorphism: the same prepareDrink() call gives a different
    // instruction for each type of drink.
    public void sendToBarista() {
        System.out.println("\n--- Barista ticket for " + orderID + " ---");
        for (int i = 0; i < count; i++) {
            items[i].prepareDrink();
        }
    }
}
