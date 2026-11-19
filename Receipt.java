// ADDITIONAL CLASS 4 - prints the receipt.
// It only PRINTS. It does not change any data (e.g. it does not add points),
// so printing the receipt twice will not cause problems.
public class Receipt {

    private String shopName;

    public Receipt(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void printReceipt(Order order, Payment payment) {
        Customer customer = order.getCustomer();

        System.out.println("\n=======================================================================");
        System.out.println("                             " + shopName);
        System.out.println("=======================================================================");
        System.out.println("Order    : " + order.getOrderID() + " (" + order.getOrderType() + ")");
        System.out.println("Customer : " + customer.getCustomerName());
        System.out.println("Phone    : " + customer.getHiddenPhone());
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < order.getCount(); i++) {
            Drink drink = order.getDrink(i);
            System.out.printf("%d x %-56s RM%6.2f%n", drink.getQuantity(),
                    drink.getDetails(), drink.calculateTotalPrice());
        }

        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-60s RM%6.2f%n", "Subtotal", order.calculateSubtotal());
        if (customer.isMember()) {
            System.out.printf("%-60s-RM%6.2f%n", "Member discount (10%)", order.calculateDiscount());
        }
        if (order.getOrderType().equals("Takeaway")) {
            System.out.printf("%-60s RM%6.2f%n", "Takeaway cups", order.calculateTakeawayFee());
        }
        System.out.printf("%-60s RM%6.2f%n", "TOTAL", order.calculateTotal());
        System.out.printf("%-60s RM%6.2f%n", "Paid by " + payment.getMethod(), payment.getAmountPaid());
        if (payment.getChange() > 0) {
            System.out.printf("%-60s RM%6.2f%n", "Change", payment.getChange());
        }
        if (customer.isMember()) {
            System.out.println("Loyalty points: " + customer.getLoyaltyPoints());
        }
        System.out.println("=======================================================================");
        System.out.println("                 Thank you for visiting " + shopName + "!");
        System.out.println("=======================================================================");
    }
}
