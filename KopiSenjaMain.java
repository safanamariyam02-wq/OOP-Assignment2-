import java.util.Scanner;

// MAIN CLASS - runs the Kopi Senja ordering system.
public class KopiSenjaMain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // The menu is a Drink[] array. It holds CoffeeDrink, NonCoffeeDrink
        // and Drink objects together, because they are all Drinks.
        Drink[] menu = new Drink[8];
        menu[0] = new CoffeeDrink("D001", "Iced Latte", 12.00, "Iced", true, 1, "Fresh Milk");
        menu[1] = new CoffeeDrink("D002", "Cappuccino", 10.00, "Hot", true, 1, "Fresh Milk");
        menu[2] = new CoffeeDrink("D003", "Americano", 8.00, "Iced", false, 1, "No Milk");
        menu[3] = new CoffeeDrink("D004", "Gula Melaka Latte", 13.50, "Iced", true, 1, "Oat Milk");
        menu[4] = new NonCoffeeDrink("D005", "Chocolate", 11.00, "Iced", true, 75, "Cream");
        menu[5] = new NonCoffeeDrink("D006", "Matcha Latte", 13.00, "Iced", true, 50, "None");
        menu[6] = new NonCoffeeDrink("D007", "Teh Tarik", 6.50, "Hot", true, 100, "None");
        menu[7] = new Drink("D008", "Mineral Water", 3.00, "Medium", "Iced", 1, true);

        Receipt receipt = new Receipt("KOPI SENJA");

        System.out.println("=================================================");
        System.out.println("            WELCOME TO KOPI SENJA!");
        System.out.println("=================================================");

        // ----- 1. Customer details -----
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        boolean member = readYesNo(input, "Are you a member? (yes/no): ");
        Customer customer = new Customer("C001", name, "Not Provided", "Not Provided", 0, member);

        if (member) {
            System.out.print("Enter your phone number (e.g. 0123456789): ");
            String phone = input.next();
            // Keep asking until the setter accepts the phone number.
            while (!customer.setPhoneNumber(phone)) {
                System.out.print("Invalid phone number. Please try again: ");
                phone = input.next();
            }
            System.out.println("Welcome, " + customer.getCustomerName() + "! You get 10% off today.");
        }

        System.out.println("Order type: 1. Dine-in   2. Takeaway (+RM0.50 per cup)");
        int typeChoice = readNumber(input, "Choose: ", 1, 2);
        String orderType = "Dine-in";
        if (typeChoice == 2) {
            orderType = "Takeaway";
        }
        Order order = new Order("KS1001", customer, orderType);

        // ----- 2. Ordering loop -----
        boolean finished = false;
        while (!finished) {
            System.out.println("\n---------------- ORDER MENU ----------------");
            System.out.println("1. View drinks menu");
            System.out.println("2. Add a drink");
            System.out.println("3. View my order");
            System.out.println("4. Remove a drink");
            System.out.println("5. Checkout and pay");
            System.out.println("0. Cancel and exit");
            int choice = readNumber(input, "Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    showMenu(menu);
                    break;
                case 2:
                    addDrink(input, menu, order);
                    break;
                case 3:
                    order.showOrder();
                    break;
                case 4:
                    removeDrink(input, order);
                    break;
                case 5:
                    if (order.getCount() == 0) {
                        System.out.println("Your order is empty. Please add a drink first.");
                    } else {
                        checkout(input, order, receipt);
                        finished = true;
                    }
                    break;
                case 0:
                    System.out.println("Order cancelled. Goodbye!");
                    finished = true;
                    break;
            }
        }

        input.close();
    }

    // Shows every drink on the menu using a for loop.
    public static void showMenu(Drink[] menu) {
        System.out.println("\n================== KOPI SENJA MENU ==================");
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %-20s %-11s RM%6.2f", i + 1, menu[i].getDrinkName(),
                    menu[i].getDrinkType(), menu[i].getPrice());
            if (!menu[i].isAvailable()) {
                System.out.print("  (SOLD OUT)");
            }
            System.out.println();
        }
        System.out.println("Small -RM1.00 | Large +RM2.00 | Extra shot +RM2.00");
        System.out.println("Oat milk +RM1.50 | Pearls / Cream topping +RM1.50");
    }

    // Lets the customer choose a drink, change it, and add it to the order.
    public static void addDrink(Scanner input, Drink[] menu, Order order) {
        showMenu(menu);
        int number = readNumber(input, "Choose a drink (0 to go back): ", 0, menu.length);
        if (number == 0) {
            return;
        }

        Drink chosen = menu[number - 1];
        if (!chosen.isAvailable()) {
            chosen.checkAvailability();
            return;
        }

        // Make a copy so the menu itself is not changed.
        Drink myDrink = chosen.makeCopy();

        int sizeChoice = readNumber(input, "Size: 1. Small  2. Medium  3. Large: ", 1, 3);
        if (sizeChoice == 1) {
            myDrink.setSize("Small");
        } else if (sizeChoice == 3) {
            myDrink.setSize("Large");
        }

        // Mineral water is always served cold, so only ask for coffee and non-coffee.
        if (!myDrink.getDrinkType().equals("Other")) {
            int tempChoice = readNumber(input, "Temperature: 1. Hot  2. Iced: ", 1, 2);
            if (tempChoice == 1) {
                myDrink.setTemperature("Hot");
            } else {
                myDrink.setTemperature("Iced");
            }
        }

        int quantity = readNumber(input, "Quantity (1-20): ", 1, 20);
        myDrink.setQuantity(quantity);

        if (order.addDrink(myDrink)) {
            System.out.printf("Added: %d x %s = RM%.2f%n", quantity,
                    myDrink.getDetails(), myDrink.calculateTotalPrice());
        } else {
            System.out.println("Sorry, your order is full (maximum 10 drinks).");
        }
    }

    public static void removeDrink(Scanner input, Order order) {
        if (order.getCount() == 0) {
            System.out.println("Your order is empty.");
            return;
        }
        order.showOrder();
        int position = readNumber(input, "Which drink to remove? (0 to go back): ", 0, order.getCount());
        if (position != 0) {
            String removedName = order.getDrink(position - 1).getDrinkName();
            order.removeDrink(position);
            System.out.println(removedName + " has been removed.");
        }
    }

    // Shows the bill, takes payment, then prints the receipt and the barista ticket.
    public static void checkout(Scanner input, Order order, Receipt receipt) {
        order.showOrder();
        double total = order.calculateTotal();
        if (order.getCustomer().isMember()) {
            System.out.printf("Member discount: -RM%.2f%n", order.calculateDiscount());
        }
        if (order.getOrderType().equals("Takeaway")) {
            System.out.printf("Takeaway cups: RM%.2f%n", order.calculateTakeawayFee());
        }
        System.out.printf("TOTAL TO PAY: RM%.2f%n", total);

        int methodChoice = readNumber(input, "Pay by: 1. Cash  2. E-Wallet: ", 1, 2);
        Payment payment;

        if (methodChoice == 1) {
            payment = new Payment(total, "Cash");
            // Keep asking until the customer gives enough cash.
            while (!payment.isPaid()) {
                double cash = readDecimal(input, "Cash given: RM");
                if (!payment.pay(cash)) {
                    System.out.printf("Not enough. You need RM%.2f.%n", total);
                }
            }
        } else {
            payment = new Payment(total, "E-Wallet");
            payment.pay();
            System.out.println("E-Wallet payment successful.");
        }

        int points = order.getCustomer().addLoyaltyPoints(total);
        receipt.printReceipt(order, payment);
        if (order.getCustomer().isMember()) {
            System.out.println("You earned " + points + " loyalty point(s) today!");
        }
        order.sendToBarista();
    }

    // ---------- Input helpers ----------
    // These keep asking until the user types a valid number,
    // so typing letters like "abc" will not crash the program.

    public static int readNumber(Scanner input, String message, int min, int max) {
        System.out.print(message);
        while (true) {
            if (input.hasNextInt()) {
                int number = input.nextInt();
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.print("Please enter a number from " + min + " to " + max + ": ");
            } else {
                input.next();   // throw away the wrong input
                System.out.print("Please enter a number: ");
            }
        }
    }

    public static double readDecimal(Scanner input, String message) {
        System.out.print(message);
        while (!input.hasNextDouble()) {
            input.next();
            System.out.print("Please enter an amount, e.g. 50.00: RM");
        }
        return input.nextDouble();
    }

    public static boolean readYesNo(Scanner input, String message) {
        System.out.print(message);
        while (true) {
            String answer = input.next();
            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.print("Please type yes or no: ");
        }
    }
}
